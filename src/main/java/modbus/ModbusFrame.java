package modbus;

import com.google.common.primitives.Bytes;
import com.google.common.primitives.Ints;
import scheme.Meter;
import scheme.Parameter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static utils.Consts.*;

/**
 * Created by crised on 7/13/14.
 */
public class ModbusFrame {

    //Modbus RTU = SlavId + FCode + Data + CRC
    //Modbus TCP = MBAP Header + FCode + Data
    //PDU = Fcode + Data
    //Modbus TCP = ADU
    //Modbus TCP = Always 8 Bytes + Data

    //MBAP HEADER - 7 Bytes
    private List<Byte> transId; // 2 Bytes
    private final List<Byte> protocolId = Arrays.asList((byte) 0x00, (byte) 0x00); // Always 0x0000 2
    private List<Byte> length; // 2 Bytes. unitId + data
    private Byte unitId;

    //PDU - 1 Byte + data
    private Byte fCode;
    private List<Byte> data; // In Request Always 4 Bytes.
    // In Response 2 X Registers Number
    // In Error Always 1 Byte.


    //Request ModbusFrame - 12 Bytes Long

    public ModbusFrame(Meter meter, Parameter parameter) throws Exception {

        this.transId = getRandomTransId();
        this.length = getLengthRequest();
        this.unitId = meter.getModbusRtuAddress();
        this.fCode = parameter.getFunctionType();
        this.data = parameter.dataInRequest();

    }

    private List<Byte> getLengthRequest() {
        List<Byte> bytes = new ArrayList<>();
        bytes.add((byte) 0x00);
        bytes.add((byte) 0x06);
        return bytes;
    }


    private List<Byte> getLength() {

        Integer count = 2 + data.size(); //unitId + data
        return Bytes.asList(Ints.toByteArray(count)).subList(0, 1);


    }


    private List<Byte> getRandomTransId() {

        byte[] b = new byte[2];
        new Random().nextBytes(b);
        return Bytes.asList(b);

    }

    public List<Byte> getMessageBytes() {
        List<Byte> message = new ArrayList<>();
        message.addAll(transId);
        message.addAll(protocolId);
        message.addAll(length);
        message.add(unitId);
        message.add(fCode);
        message.addAll(data);
        return message;
    }


}
