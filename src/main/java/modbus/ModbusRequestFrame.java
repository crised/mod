package modbus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scheme.GroupMessage;

import java.nio.ByteBuffer;

import java.util.Random;

/**
 * Created by crised on 7/18/14.
 */
public class ModbusRequestFrame extends Frame {

    static final Logger LOG = LoggerFactory.getLogger("ModbusRequestFrame");


    public ModbusRequestFrame(GroupMessage groupMessage) {
        super();
        //Request ModbusFrame - 12 Bytes Long
        this.transId = getRandomTransId();
        this.transId.rewind();
        this.length = getRequestLength();
        this.length.rewind();
        this.unitId = groupMessage.getMeter().getModbusRtuAddress();
        this.fCode = groupMessage.getFunctionCode();
        this.data = groupMessage.getRequestData();
        this.data.rewind();
    }

    private ByteBuffer getRandomTransId() {
        //It's not sequential.
        byte[] b = new byte[2];
        new Random().nextBytes(b);
        return ByteBuffer.wrap(b);
    }

    private ByteBuffer getRequestLength() {
        return ByteBuffer.allocate(2).put((byte) 0x00).put((byte) 0x06);
    }

    public ByteBuffer getMessageBytes() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(12).put(transId).put(protocolId)
                .put(length).put(unitId).put(fCode).put(data);
        byteBuffer.rewind();
        return  byteBuffer;
    }


}
