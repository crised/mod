package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crised on 7/9/14.
 */
public class Client {

    static final Logger LOG = LoggerFactory.getLogger("Client");

    private static final String ip = "localhost";
    private static final int port1 = 8001;
    private static final byte[] bytes = {0x01, 0x02};


    public static void main(String args[]) {

        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {

            //Cliente

            Bootstrap b = new Bootstrap();
            b.group(bossGroup)
                    .channel(NioSocketChannel.class)
                            //.option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInit());

            ChannelFuture f = b.connect(ip, port1);
            //Cuando se conecte
            f.addListener(new ChannelFutureListener() {

                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    Channel channel = channelFuture.channel();
                    channel.writeAndFlush(Unpooled.copiedBuffer(bytes));
                    channel.writeAndFlush(Unpooled.copiedBuffer(bytes));

                }


            });

            // f.addListener(ChannelFutureListener.CLOSE);
            f.channel().closeFuture().sync();


        } catch (Exception e) {
            LOG.error(e.getMessage());
        } finally {
            bossGroup.shutdownGracefully();
        }


    }
}
