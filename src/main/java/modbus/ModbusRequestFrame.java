package modbus;

import scheme.GroupMessage;

import java.nio.ByteBuffer;

import java.util.Random;

/**
 * Created by crised on 7/18/14.
 */
public class ModbusRequestFrame extends Frame {

    public ModbusRequestFrame(GroupMessage groupMessage) {
        //Request ModbusFrame - 12 Bytes Long
        this.transId = getRandomTransId();
        this.length = getRequestLength();
        this.unitId = groupMessage.getMeter().getModbusRtuAddress();
        this.fCode = groupMessage.getFunctionCode();
        this.data = groupMessage.getRequestData();
    }

    private ByteBuffer getRandomTransId() {
        byte[] b = new byte[2];
        new Random().nextBytes(b);
        return ByteBuffer.wrap(b);
    }

    private ByteBuffer getRequestLength() {
        return ByteBuffer.allocate(2).put((byte) 0x00).put((byte) 0x06);
    }

    public ByteBuffer getMessageBytes() {
        return ByteBuffer.allocate(12).put(transId).put(protocolId)
                .put(length).put(unitId).put(fCode).put(data);
    }


}
