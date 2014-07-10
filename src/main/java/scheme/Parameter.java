package scheme;

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
    public final Integer functionType;

    public List<Byte> bytes;


    public Parameter(String name, Integer start_register, Integer end_register) {
        this.Id = name;
        this.start_register = start_register;
        this.end_register = end_register;
        this.checkInterval = INTERVAL_DEFAULT;
        this.functionType = READ_HOLDING_REGISTERS;
    }
}
