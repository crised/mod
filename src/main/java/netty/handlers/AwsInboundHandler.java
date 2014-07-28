package netty.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crised on 7/26/14.
 */
public class AwsInboundHandler extends ChannelInboundHandlerAdapter {

    static final Logger LOG = LoggerFactory.getLogger("AwsInboundHandler");

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOG.info("");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        LOG.info("");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        LOG.error(cause.getMessage());


    }
}
