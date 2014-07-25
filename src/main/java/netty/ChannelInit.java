package netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import netty.handlers.FirstInboundHandler;
import netty.handlers.ModbusDecoder;
import netty.handlers.ModbusEncoder;
import netty.handlers.LastOutboundHandler;
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
        pipeline.addLast("Outbound 1", new LastOutboundHandler());
        pipeline.addLast("Outbound 2", new ModbusEncoder());

        pipeline.addLast("Inbound 3", new FirstInboundHandler());
        pipeline.addLast("Inbound 4", new ModbusDecoder());

        //Inbound 1,2,3,4
        //Outbound 4,3,2,1




    }
}
