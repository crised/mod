package scheme;


import com.google.common.primitives.Ints;
import modbus.ModbusErrorFrame;
import modbus.ModbusResponseFrame;
import utils.AppException;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * Created by crised on 7/15/14.
 */
public class GroupMessage {

    //Gateway & Table Name & Modbus Address & Parameters
    //Where Values will be stored.
    //Same Function
    //Same Frequency
    private final Meter meter;

    //Response Data Goes in the responseBytes of Parameter
    private final List<Parameter> parameterList;

    private final Integer firstQueryRegister;
    private final Integer quantityOfRegisters;
    private final Integer frequency;
    private final Byte functionCode;
    private final Integer parameterSize;

    private final ByteBuffer requestData;

    private int passes;


    public GroupMessage(Meter meter, List<Parameter> parameterList) {

        this.meter = meter;
        this.parameterList = parameterList;
        this.firstQueryRegister = parameterList.get(0).getStartRegister();
        this.quantityOfRegisters = (parameterList.get(parameterList.size() - 1).getEndRegister() - firstQueryRegister + 1);
        this.frequency = parameterList.get(0).getFrequency();
        this.functionCode = parameterList.get(0).getFunctionType();
        this.parameterSize = parameterList.get(0).getSize();
        this.requestData = ByteBuffer.allocate(4).put(Ints.toByteArray(firstQueryRegister), 2, 2).
                put(Ints.toByteArray(quantityOfRegisters), 2, 2);


    }

    public boolean isMyTurn() {
        passes++;
        if (passes % frequency == 0) return true;
        return false;
    }

    public void setResponse(ModbusResponseFrame responseFrame) throws Exception {

        if (responseFrame.getByteCount() != parameterList.size() * 2)
            throw new AppException("Quantity of Register Do Not Match");

        ByteBuffer regValues = responseFrame.getData();

        for (Parameter parameter : parameterList) {
            parameter.setResponseBytes(ByteBuffer.allocate(2).put(regValues.get()).put(regValues.get()));
        }

    }

    public void setResponse(ModbusErrorFrame errorFrame) {

        //Leave this empty. Just log errorFrame on inboundHandler.

    }


    public Meter getMeter() {
        return meter;
    }

    public List<Parameter> getParameterList() {
        return parameterList;
    }

    public Byte getFunctionCode() {
        return functionCode;
    }

    public ByteBuffer getRequestData() {
        return requestData;
    }

}
