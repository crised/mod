package utils;

/**
 * Created by crised on 7/9/14.
 */
public final class Consts {

    private Consts() {
    }

    //Intervals
    public static final int INTERVAL_DEFAULT = 900; // Query Everything every 900 seconds - 15 min

    //Modbus 16 bit access
    public static final int READ_HOLDING_REGISTERS = 0x03;
    public static final int READ_INPUT_REGISTERS = 0x04; // Read-Only

    //Modbus Exceptions
    public static final int MODBUS_ILLEGAL_FUNCTION = 0x02;
    public static final int MODBUS_ILLEGAL_DATA_ADDRESS = 0x02;
    public static final int MODBUS_ILLEGAL_DATA_VALUE = 0x03;
    public static final int MODBUS_FAILURE_IN_DEVICE = 0x04;
    public static final int MODBUS_ACKNOWLEDGE = 0x05;
    public static final int MODBUS_READ_HOLDING_REGISTERS_ERROR = 0x83;
    public static final int MODBUS_READ_INPUT_REGISTERS_ERROR = 0x84;
    //TODO: Finish Modbus exceptions


    //Meter Names
    public static final String METER_CPP_1 = "cpp_1";

    //Parameter names
    public static final String VOLT_PHASE_1_EL = "volt_phase_1_el";
    public static final String VOLT_PHASE_2_EL = "volt_phase_2_el";
    public static final String VOLT_PHASE_3_EL = "volt_phase_3_el";

}
