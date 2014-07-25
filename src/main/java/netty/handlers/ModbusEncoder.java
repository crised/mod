package netty.handlers;

import com.google.common.primitives.Bytes;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.List;

/**
 * Created by crised on 7/12/14.
 */
public class ModbusEncoder extends MessageToByteEncoder<Object> {

    static final Logger LOG = LoggerFactory.getLogger("ModbusEncoder");

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        LOG.info("");
        super.read(ctx);
    }

    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        LOG.info("");

        super.connect(ctx, remoteAddress, localAddress, promise);
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        LOG.info("");

        super.disconnect(ctx, promise);
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        LOG.info("");

        super.close(ctx, promise);
    }

    @Override
    public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        LOG.info("");

        super.deregister(ctx, promise);
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {

        LOG.info("encoder");
        byteBuf.writeBytes((ByteBuffer) o);

    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOG.warn(cause.getMessage());
        super.exceptionCaught(ctx, cause);
    }
}
