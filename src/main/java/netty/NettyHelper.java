package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import modbus.ModbusRequestFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scheme.Gateway;
import scheme.GroupMessage;
import utils.AppException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static utils.Consts.*;


/**
 * Created by crised on 7/12/14.
 */
public class NettyHelper {

    static final Logger LOG = LoggerFactory.getLogger("Helper");

    //Netty vars
    private final EventLoopGroup bossGroup;
    private final Bootstrap b;


    //App Vars
    private final Map<Gateway, ChannelFuture> channelFutureMap = new HashMap<>();
    private final Map<ChannelFuture, Bootstrap> bootstrapMap = new HashMap<>();
    private List<Gateway> gatewayList;

    public NettyHelper(List<Gateway> gatewayList) {

        this.gatewayList = gatewayList;

        //Netty Bootstrap Client
        bossGroup = new NioEventLoopGroup();

        b = new Bootstrap();
        b.group(bossGroup)
                .channel(NioSocketChannel.class)
                        //.channel(OioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInit());

        // 1 bootstrap object per each channel. cloned.
        // Connect all Channels
        for (Gateway gateway : this.gatewayList) {
            Bootstrap bootstrap = b.clone();
            ChannelFuture future = bootstrap.clone().remoteAddress(gateway.getHost(), gateway.getPort()).connect();
            addListeners(future);
            bootstrapMap.put(future, bootstrap);
            channelFutureMap.put(gateway, future);
        }
    }


    public void sendOneGroupMessage(final GroupMessage groupMessage) {

        try {

            final ModbusRequestFrame request = new ModbusRequestFrame(groupMessage);
            //LOG.info("Message is: " + utils.Utils.bytesToHex(message.array()));

            Channel channel = getActiveChannel(groupMessage.getMeter().getGateway());


            ChannelFuture f = channel.writeAndFlush(request.getMessageBytes()); //Flush immediately so 1 call equals one tcp frame.
            f.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        LOG.info("Message flushed is: " + utils.Utils.bytesToHex(request.getMessageBytes().array()));
                        transIdMap.put(request.getTransId().getInt(), groupMessage.hashCode());
                    } else {
                        throw new AppException("Couldn't flush");
                    }
                }
            });

        } catch (AppException e) {
            LOG.warn("AppException: " + e.getMessage());
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }


    }


    private Channel getActiveChannel(Gateway gateway) throws Exception {

        ChannelFuture channelFuture = channelFutureMap.get(gateway);
        if (channelFuture == null) throw new AppException(CHANNEL_INIT);
        if (!channelFuture.isDone()) throw new AppException(FUTURE_CHANNEL_IS_NOT_DONE);
        if (!channelFuture.isSuccess()) throw new AppException(CHANNEL_UNSUCCESSFUL);
        if (!channelFuture.channel().isActive()) throw new AppException(CHANNEL_CLOSED);
        return channelFuture.channel();
    }


    //Self Healing Channels
    private void addListeners(final ChannelFuture future) {

        //Keep Trying until it connects.
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture f) throws Exception {
                if(f.channel().isActive()) LOG.info("Channel Is Active");
                if (!f.isSuccess()) {
                    f.channel().eventLoop().schedule(new Runnable() {
                        @Override
                        public void run() {
                            LOG.info("Retrying to Connect");
                            ChannelFuture newFuture = bootstrapMap.get(future).connect();
                            addListeners(newFuture);
                        }
                    }, 5, TimeUnit.SECONDS); //Exponential Backoff should be here.
                }
            }
        });

        //Reconnect When it closes.
        future.channel().closeFuture().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(final ChannelFuture f) throws Exception {
                f.channel().eventLoop().schedule(new Runnable() {
                    @Override
                    public void run() {
                        LOG.info("Retrying to Connect because Channel was closed.");
                        ChannelFuture newFuture = bootstrapMap.get(future).connect();
                        addListeners(newFuture);
                    }
                }, 5, TimeUnit.SECONDS);
            }
        });
    }

    public void shutDown() {
        for (ChannelFuture channelFuture : channelFutureMap.values()) {
            channelFuture.channel().close(); // Close the connections.
        }
        bossGroup.shutdownGracefully();
    }


}
