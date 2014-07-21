package server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by crised on 7/21/14.
 */
public class ServerChannelInit extends ChannelInitializer<SocketChannel> {

    static final Logger LOG = LoggerFactory.getLogger("ServerChannelInit");

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        LOG.info("Channel Init");
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new IncomingHandler());
        LOG.info("Channel Initialized");

    }


}
