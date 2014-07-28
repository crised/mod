package netty.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crised on 7/21/14.
 */
public class FirstInboundHandler extends ChannelInboundHandlerAdapter {

    static final Logger LOG = LoggerFactory.getLogger("FirstInboundHandler");




    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.READER_IDLE) {
                LOG.info("Reader Idle event - Closing Channel");
                ctx.close();
            } else if (e.state() == IdleState.WRITER_IDLE) LOG.error("Application is not sending messages!");

        }
    }


    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOG.error("exception!");
        super.exceptionCaught(ctx, cause);
    }


}
