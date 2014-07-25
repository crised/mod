package netty.handlers;

import com.google.common.primitives.Bytes;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * Created by crised on 7/12/14.
 */
public class ModbusEncoder extends MessageToByteEncoder<Object> {

    static final Logger LOG = LoggerFactory.getLogger("ModbusEncoder");

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {

        LOG.info("encoder");
        byteBuf.writeBytes((ByteBuffer) o);

    }
}