package modbus;

import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.AppException;

import java.nio.ByteBuffer;

/**
 * Created by crised on 7/18/14.
 */
public class ModbusResponseFrame extends Frame {

    static final Logger LOG = LoggerFactory.getLogger("ModbusResponseFrame");

    private Byte byteCount; //Only in response.


    public ModbusResponseFrame(ByteBuf in) throws Exception {

        super();

        //{transId, TransId, ProtocolId, ProtocolId,
        //{length, length, unitId, fCode,
        //{byte1_1, byte2_1, byte1_2, byte2_2... n x2.

        //ByteBuffer.wrap(in.array(), 4, 2);

        in.resetReaderIndex(); //readerIndex to 0
        LOG.info(in.toString());

        //Modbus TCP = 7 Bytes
        this.transId = ByteBuffer.allocate(2).put(in.readByte()).put(in.readByte());

        //Put these separate and not in the if clause.
        Byte protocolIdByte1 = in.readByte(); //value 0
        Byte protocolIdByte2 = in.readByte(); //value 0
        Byte zero = (byte) 0;

        if (!protocolIdByte1.equals(zero) || !protocolIdByte2.equals(zero))
            throw new AppException("Protocol Id Should be 0x0000");

        this.length = ByteBuffer.allocate(2).put(in.readByte()).put(in.readByte());
        this.unitId = in.readByte();

        //Modbus - PDU = 2 + Data (2*N Registers)
        this.fCode = in.readByte();
        this.byteCount = in.readByte(); //Only in response Frame.

        //LOG.info("Reader Index Should be 9: " + String.valueOf(in.readerIndex()));

        int remainingBytes = in.readableBytes();

        if (remainingBytes % 2 != 0) throw new AppException("Register Values Should be Pair. N*2");

        byte[] bytes = new byte[remainingBytes];
        in.readBytes(bytes);
        this.data = ByteBuffer.wrap(bytes);


    }

    public Byte getByteCount() {
        return byteCount;
    }
}
