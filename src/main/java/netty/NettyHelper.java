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

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utils.Utils.bytesToHex;
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
    private final Map<Gateway, Bootstrap> bootstrapMap = new HashMap<>();
    private final Map<Gateway, Channel> channelMap = new HashMap<>();
    private List<Gateway> gatewayList;

    public NettyHelper(List<Gateway> gatewayList) {

        this.gatewayList = gatewayList;

        //Netty Bootstrap Client
        bossGroup = new NioEventLoopGroup();

        b = new Bootstrap();
        b.group(bossGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true) // Robustel M1000Pro Supports it.
                .handler(new ChannelInit());

        // 1 bootstrap object per each channel. cloned.
        // Connect all Channels
        for (Gateway gateway : this.gatewayList) {
            bootstrapMap.put(gateway, b.clone().remoteAddress(gateway.getHost(), gateway.getPort()));
            activateChannel(gateway);
        }

    }


    public void sendOneGroupMessage(final GroupMessage groupMessage) {

        //Ojo que el GroupMessage se clone.
        LOG.info("sendOneGroupMessage");

        final Gateway gateway = groupMessage.getMeter().getGateway();

        final ModbusRequestFrame request = new ModbusRequestFrame(groupMessage);
        ByteBuffer message = request.getMessageBytes();
        LOG.info("Message is: " + utils.Utils.bytesToHex(message.array()));


        final Channel channel = channelMap.get(gateway);

        ChannelFuture f = channel.writeAndFlush(message); //Flush immediately so 1 call equals one tcp frame.
        f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    LOG.info("Could Write!");
                    //Message were sent, so I need to write down transaction.
                    transIdMap.put(request.getTransId().getInt(), groupMessage.hashCode());
                } else {
                    LOG.error("couldn't flush");
                    channelFuture.channel().close();
                    channelFuture.addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture channelFuture) throws Exception {
                            LOG.error("removing channel");
                            channelMap.remove(channel); //Only once it's closed we delete it. //We avoid having 2 connections with one Gateway.
                            activateChannel(gateway);
                        }
                    });
                }
            }
        });
    }


    private void activateChannel(final Gateway gateway) {

        LOG.info("Activating Channel");
        Bootstrap b = bootstrapMap.get(gateway);


        b.connect().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    LOG.info("channel connected");
                    channelMap.put(gateway, channelFuture.channel());
                } else {
                    LOG.error(channelFuture.cause().getMessage());
                }
            }
        });
    }

    public void shutDown() {
        for (Channel channel : channelMap.values()) {
            channel.close(); // Close the connections.
        }
        bossGroup.shutdownGracefully();
    }


}
