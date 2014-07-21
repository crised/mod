package server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Utils;


/**
 * Created by crised on 7/21/14.
 */
public class IncomingHandler extends ChannelInboundHandlerAdapter {

    static final Logger LOG = LoggerFactory.getLogger("IncomingHandler");

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        LOG.info("");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOG.info("");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        byte[] bytes = new byte[in.writerIndex()];
        in.getBytes(0,bytes);

        LOG.info(Utils.bytesToHex(bytes));
        in.release();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        LOG.error(cause.getMessage());
        LOG.error(cause.getCause().getMessage());
    }
}
