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
    private final Map<Gateway, Bootstrap> bootstrapMap = new HashMap<>();
    private List<Gateway> gatewayList;

    public NettyHelper(List<Gateway> gatewayList) {

        this.gatewayList = gatewayList;

        //Netty Bootstrap Client
        bossGroup = new NioEventLoopGroup();

        b = new Bootstrap();
        b.group(bossGroup)
                .channel(NioSocketChannel.class)
                        //.channel(OioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true) //Every 2 hours
                .option(ChannelOption.TCP_NODELAY, true) //send messages whenever they're ready.
                .handler(new ChannelInit());

        // 1 bootstrap object per each channel. cloned.
        // Connect all Channels
        for (Gateway gateway : this.gatewayList) {
            Bootstrap bootstrap = b.clone().remoteAddress(gateway.getHost(), gateway.getPort());
            bootstrapMap.put(gateway, bootstrap);
            doConnect(gateway);

        }
    }

    //Self Healing Channels
    private void doConnect(final Gateway gateway) {

        final ChannelFuture future = bootstrapMap.get(gateway).connect();

        //First time Called
        if (channelFutureMap.get(gateway) == null) channelFutureMap.put(gateway, future);

        //Keep Trying until it connects.
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture f) throws Exception {
                if (!f.isSuccess()) {
                    f.channel().eventLoop().schedule(new Runnable() {
                        @Override
                        public void run() {
                            LOG.info("Failed to Connect - Retrying to Connect");
                            doConnect(gateway);
                        }
                    }, 15, TimeUnit.SECONDS); //Exponential Backoff should be here.
                } else{
                    LOG.info("Channel Connected - Adding close Listener");
                    f.channel().closeFuture().addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture future) throws Exception {
                            LOG.info("Channel Closed - Retrying to Connect");
                            doConnect(gateway);
                        }
                    });
                }


            }
        });
    }

    public void sendOneGroupMessage(final GroupMessage groupMessage) {

        try {

            final ModbusRequestFrame request = new ModbusRequestFrame(groupMessage);
           LOG.info("sendOneGroupMessage");

            Channel channel = getActiveChannel(groupMessage.getMeter().getGateway());


            ChannelFuture f = channel.writeAndFlush(request.getMessageBytes()); //Flush immediately so 1 call equals one tcp frame.
            f.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        LOG.info("Message flushed");
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
       // if (!channelFuture.channel().isActive()) throw new AppException(CHANNEL_INACTIVE);
       // if (!channelFuture.channel().isOpen())   throw new AppException(CHANNEL_CLOSED);

        return channelFuture.channel();
    }

    public void shutDown() {
        for (ChannelFuture channelFuture : channelFutureMap.values()) {
            channelFuture.channel().close(); // Close the connections.
        }
        bossGroup.shutdownGracefully();
    }


}
