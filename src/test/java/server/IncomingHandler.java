package server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crised on 7/21/14.
 */
public class IncomingHandler extends ChannelInboundHandlerAdapter {

    static final Logger LOG = LoggerFactory.getLogger("TestScheme");


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf byteBuf = (ByteBuf) msg;
        byteBuf.release();


    }




}
