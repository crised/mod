package scheme;


import java.util.List;

/**
 * Created by crised on 7/15/14.
 */
public class ModbusMessage {

    //Gateway & Table Name & Modbus Address & Parameters
    //Where Values will be stored.
    //Same Function
    //Same Frequency
    private Meter meter;
    private List<Parameter> parameterList;

    private Integer firstQueryRegister;
    private Integer lastQueryRegister;
    private Integer frequency;
    private Byte functionCode;

    public ModbusMessage(Meter meter, List<Parameter> parameterList) {
        this.meter = meter;
        this.parameterList = parameterList;
        this.firstQueryRegister = parameterList.get(0).getStartRegister();
        this.lastQueryRegister = parameterList.get(parameterList.size() - 1).getEndRegister();
        this.frequency = parameterList.get(0).getFrequency();
        this.functionCode = parameterList.get(0).getFunctionType();
    }
}
