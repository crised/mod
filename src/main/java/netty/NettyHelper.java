package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
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
                        //.option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInit());

        // 1 bootstrap object per each channel. cloned.
        for (Gateway gateway : gateways) {
            bootstrapMap.put(gateway, b.clone().remoteAddress(gateway.getHost(), gateway.getPort()));
        }

    }


    private void sendMessagesTo(final Gateway gateway, List<List<Byte>> messages) {

        if (channelMap.get(gateway) == null) activateChannel(gateway); //It might take time to reconnect.

        final Channel channel = channelMap.get(gateway);
        if (channel == null) return; //Channel is still not ready, Maybe will be ready on next method call.

        for (List<Byte> message : messages) {
            ChannelFuture f = channel.writeAndFlush(message); //Flush immediately so 1 call equals one tcp frame.
            f.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if(!channelFuture.isSuccess()){
                        channelMap.remove(channel);
                    }
                }
            });
        }
    }


    private void activateChannel(final Gateway gateway) {

        Bootstrap b = bootstrapMap.get(gateway);

        b.connect().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    channelMap.put(gateway, channelFuture.channel());
                } else {
                    LOG.error("App Error");
                }
            }
        });
    }


    public void shutDown() {
        bossGroup.shutdownGracefully();

    }




    /*  ChannelFuture f = b.connect(ip, port1);
            //Cuando se conecte
            f.addListener(new ChannelFutureListener() {

                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    Channel channel = channelFuture.channel();
                    channel.writeAndFlush(Unpooled.copiedBuffer(bytes));

                }


            });*/
}
