package scheme;

import com.google.common.primitives.Bytes;
import com.google.common.primitives.Ints;
import utils.ModException;

import java.util.ArrayList;
import java.util.List;

import static utils.Consts.*;

/**
 * Created by crised on 7/9/14.
 */
public class Parameter {

    public final String Id; //low case, attribute Id, column Id. THIS IS A PRIMARY KEY - Uniquer to any Parameter.
    public final Integer start_register;
    public final Integer end_register;

    public final Integer checkInterval; // % operator returns the remainder of two numbers
    public final Byte functionType;

    public List<Byte> bytesRead;

    public Parameter(String id, Integer start_register, Integer end_register, Integer checkInterval, Byte functionType) {
        this.Id = id;
        this.start_register = start_register;
        this.end_register = end_register;
        this.checkInterval = checkInterval;
        this.functionType = functionType;
    }

    public List<Byte> dataInRequest() throws Exception{

        List<Byte> bytes = new ArrayList<>();
        bytes.addAll(Bytes.asList(Ints.toByteArray(start_register)).subList(2,4));

        int start_register = this.start_register;
        int end_register = this.end_register;

        int numberOfRegisters = end_register - start_register;

        if(numberOfRegisters>=125) throw new ModException("Quantity of registers are too much");

        bytes.addAll(Bytes.asList(Ints.toByteArray(numberOfRegisters)).subList(2,4));

        return bytes;


    }

    public String getId() {
        return Id;
    }

    public Integer getStart_register() {
        return start_register;
    }

    public Integer getEnd_register() {
        return end_register;
    }

    public Integer getCheckInterval() {
        return checkInterval;
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
