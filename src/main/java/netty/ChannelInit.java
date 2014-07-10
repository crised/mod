package netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import netty.outHandlers.LastOutboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crised on 7/10/14.
 */
public class ChannelInit extends ChannelInitializer {

    static final Logger LOG = LoggerFactory.getLogger("ChannelInit");


    @Override
    protected void initChannel(Channel channel) throws Exception {

        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast("Last Outbound Handler", new LastOutboundHandler());


    }
}
