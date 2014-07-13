package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scheme.Gateway;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by crised on 7/12/14.
 */
public class NettyHelper {

    static final Logger LOG = LoggerFactory.getLogger("Helper");

    //Netty vars
    private final EventLoopGroup bossGroup;
    private final Bootstrap b;


    private final Map<Gateway, Bootstrap> bootstrapMap = new HashMap<>();
    private final Map<Gateway, Channel> channelMap = new HashMap<>();

    public NettyHelper(List<Gateway> gateways) {

        //Netty Bootstrap
        bossGroup = new NioEventLoopGroup();

        b = new Bootstrap();
        b.group(bossGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true) // Robustel M1000Pro Supports it.
                .handler(new ChannelInit());

        // 1 bootstrap object per each channel. cloned.
        for (Gateway gateway : gateways) {
            bootstrapMap.put(gateway, b.clone().remoteAddress(gateway.getHost(), gateway.getPort()));
        }

    }


    public void sendMessagesTo(final Gateway gateway, List<Byte> message) {

        if (channelMap.get(gateway) == null) activateChannel(gateway); //It might take time to reconnect.

        final Channel channel = channelMap.get(gateway);
        if (channel == null) return; //Channel is still not ready, Maybe will be ready on next method call.

            ChannelFuture f = channel.writeAndFlush(message); //Flush immediately so 1 call equals one tcp frame.
            f.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (!channelFuture.isSuccess()) {
                        channelFuture.channel().close();
                        channelMap.remove(channel); //Something might be wrong with the Channel. So we delete it.
                        LOG.error("couldn't flush");
                    } else{
                        LOG.info("message sent");
                    }
                }
            });
    }


    private void activateChannel(final Gateway gateway) {

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
        for(Channel channel : channelMap.values()){
            channel.close(); // Close the connections.
        }
        bossGroup.shutdownGracefully();
    }




}
