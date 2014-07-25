package netty.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crised on 7/21/14.
 */
public class FirstInboundHandler extends ChannelInboundHandlerAdapter {

    static final Logger LOG = LoggerFactory.getLogger("FirstInboundHandler");


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LOG.info("channel Inactive!");
        ctx.channel().close();
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        LOG.info("channel Inactive!");

        super.channelRead(ctx, msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOG.info("channel Active!");

        super.channelActive(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        LOG.info("channel registered!");

        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        LOG.info("channel unregistered!");

        super.channelUnregistered(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        LOG.info("channel Inactive!");

        super.channelReadComplete(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        LOG.info("channel Inactive!");

        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        LOG.info("channel Inactive!");

        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOG.info("exception!");


        super.exceptionCaught(ctx, cause);
    }
}
