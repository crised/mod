package modbus;

import com.google.common.primitives.Bytes;
import com.google.common.primitives.Ints;
import scheme.Meter;
import scheme.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by crised on 7/12/14.
 */
public class MessageBuilder {

    public static List<Byte> ModbusMessage(Meter meter, Parameter parameter){

        List<Byte> bytes = new ArrayList<>();
        bytes.add(meter.getModbusRtuAddress());
        bytes.add(parameter.getFunctionType());

        bytes.addAll(Bytes.asList(Ints.toByteArray(parameter.getStart_register())));
        bytes.add(parameter.numberOfRegisters());

        return bytes;



    }

}
