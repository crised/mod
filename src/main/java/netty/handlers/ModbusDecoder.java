package netty.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import modbus.ModbusResponseFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Utils;

import java.util.List;

/**
 * Created by crised on 7/18/14.
 */
public class ModbusDecoder extends ByteToMessageDecoder {

    // To either ModbusResponseFrame or ModbusErrorFrame

    static final Logger LOG = LoggerFactory.getLogger("ModbusDecoder");

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LOG.info("");
        super.channelInactive(ctx);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> objects) throws Exception {

        byte[] bytes = new byte[byteBuf.writerIndex()];
        byteBuf.duplicate().getBytes(0, bytes);
        LOG.info(Utils.bytesToHex(bytes));


        byteBuf.release();





    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOG.warn(cause.getMessage());

        super.exceptionCaught(ctx, cause);
    }
}
