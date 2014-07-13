package netty.outHandlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;

/**
 * Created by crised on 7/10/14.
 */
public class LastOutboundHandler extends ChannelOutboundHandlerAdapter {

    static final Logger LOG = LoggerFactory.getLogger("LastOutboundHandler");




    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        LOG.warn(cause.getMessage());
    }
}
