package scheme;

import com.google.common.primitives.Bytes;
import com.google.common.primitives.Ints;
import utils.ModException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by crised on 7/9/14.
 */
public class Parameter {

    private final String Id; //low case, attribute Id, column Id. THIS IS A PRIMARY KEY - Uniquer to any Parameter.
    private final Integer startRegister;
    private final Integer endRegister;

    private final Byte functionType;
    private final Integer frequency; // % operator returns the remainder of two numbers


    private List<Byte> bytesRead;

    public Parameter(String id, Integer startRegister, Integer endRegister, Byte functionType, Integer frequency) {
        Id = id;
        this.startRegister = startRegister;
        this.endRegister = endRegister;
        this.functionType = functionType;
        this.frequency = frequency;
    }

    public List<Byte> dataInRequest() throws Exception{

        List<Byte> bytes = new ArrayList<>();
        bytes.addAll(Bytes.asList(Ints.toByteArray(startRegister)).subList(2,4));

        int start_register = this.startRegister;
        int end_register = this.endRegister;

        int numberOfRegisters = end_register - start_register;

        if(numberOfRegisters>=125) throw new ModException("Quantity of registers are too much");

        bytes.addAll(Bytes.asList(Ints.toByteArray(numberOfRegisters)).subList(2,4));

        return bytes;


    }

    public String getId() {
        return Id;
    }

    public Integer getStartRegister() {
        return startRegister;
    }

    public Integer getEndRegister() {
        return endRegister;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public Byte getFunctionType() {
        return functionType;
    }

    public List<Byte> getBytesRead() {
        return bytesRead;
    }

    public void setBytesRead(List<Byte> bytesRead) {
        this.bytesRead = bytesRead;
    }
}
