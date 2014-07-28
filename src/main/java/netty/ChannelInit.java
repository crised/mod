package netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.IdleStateHandler;
import netty.handlers.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static utils.Consts.*;

/**
 * Created by crised on 7/10/14.
 */
public class ChannelInit extends ChannelInitializer {

    static final Logger LOG = LoggerFactory.getLogger("ChannelInit");


    @Override
    protected void initChannel(Channel channel) throws Exception {

        ChannelPipeline pipeline = channel.pipeline();

        pipeline.addLast("Idle State", new IdleStateHandler(READER_IDLE_TIME, WRITER_IDLE_TIME, ALL_IDLE_TIME));

        pipeline.addLast("Outbound 1", new LastOutboundHandler());
        pipeline.addLast("Outbound 2", new ModbusEncoder());

        pipeline.addLast("Inbound 3", new FirstInboundHandler());
        pipeline.addLast("Inbound 4", new ModbusDecoder());
        pipeline.addLast("Inbound 5", new AwsInboundHandler());

        //Inbound 1,2,3,4
        //Outbound 4,3,2,1




    }
}
