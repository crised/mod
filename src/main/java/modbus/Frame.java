package modbus;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by crised on 7/13/14.
 */
public abstract class Frame {

    //Modbus RTU = SlavId + FCode + Data + CRC
    //Modbus TCP = MBAP Header + FCode + Data
    //PDU = Fcode + Data
    //Modbus TCP = ADU
    //Modbus TCP = Always 8 Bytes + Data

    //MBAP HEADER - 7 Bytes
    protected ByteBuffer transId; // 2 Bytes
    protected final ByteBuffer protocolId = ByteBuffer.allocate(2).put((byte) 0).put((byte) 0); // Always 0x0000 2
    protected ByteBuffer length; // 2 Bytes. unitId + data
    protected Byte unitId;

    //PDU - 1 Byte + data
    protected Byte fCode;

    //Data
    protected ByteBuffer data;



    public ByteBuffer getTransId() {
        return transId;
    }

    public ByteBuffer getProtocolId() {
        return protocolId;
    }

    public ByteBuffer getLength() {
        return length;
    }

    public Byte getUnitId() {
        return unitId;
    }

    public Byte getfCode() {
        return fCode;
    }

    public ByteBuffer getData() {
        return data;
    }
}
