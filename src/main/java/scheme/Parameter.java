package scheme;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * Created by crised on 7/9/14.
 */
public class Parameter {

    //Modbus up to 65536 in register address.
    // 1 Modbus Register 16 bits
    // Parameters can have either 1 register or 2, 16 or 32 Bits.

    private final String Id; //low case, attribute Id, column Id. THIS IS A PRIMARY KEY - Uniquer to any Parameter.
    private final Integer startRegister;
    private final Integer endRegister;
    private final Integer size; //1 or 2

    private final Byte functionType;
    private final Integer frequency; // % operator returns the remainder of two numbers


    private ByteBuffer responseBytes; //Goes Directly to DynamoDB.

    public Parameter(String id, Integer startRegister, Integer endRegister, Byte functionType, Integer frequency) {
        Id = id;
        this.startRegister = startRegister;
        this.endRegister = endRegister;
        this.size = (endRegister - startRegister + 1);
        this.functionType = functionType;
        this.frequency = frequency;

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

    public Integer getSize() {
        return size;
    }

    public Byte getFunctionType() {
        return functionType;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public ByteBuffer getResponseBytes() {
        return responseBytes;
    }

    public void setResponseBytes(ByteBuffer responseBytes) {
        this.responseBytes = responseBytes;
    }
}
