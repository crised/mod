package utils;

import scheme.GroupMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by crised on 7/9/14.
 */
public final class Consts {

    private Consts() {
    }

    //Hash Id of every GroupMessage
    public final static Map<Integer, GroupMessage> groupMessageMap = new ConcurrentHashMap<>();

    //TransMap
    public final static Map<Integer, Integer> transIdMap = new ConcurrentHashMap<>();
    //TransId will be deleted once stored in the Queue.


    public final static List<GroupMessage> groupMessageQueue = new ArrayList<>();
    //Once stored in DynamoDb, it's removed.


    public static final int MODBUS_TCP_PORT = 502; //502


    //Intervals
    public static final int INTERVAL_DEFAULT = 900; // Query Everything every 900 seconds - 15 min

    //modbus 16 bit access
    public static final Byte READ_HOLDING_REGISTERS = (byte) 0x03;
    public static final Byte READ_INPUT_REGISTERS = (byte) 0x04; // Read-Only
    public static final Integer MODBUS_MAX_ADDRESS = 65535;


    //modbus Exceptions
    public static final int MODBUS_ILLEGAL_FUNCTION = 0x02;
    public static final int MODBUS_ILLEGAL_DATA_ADDRESS = 0x02;
    public static final int MODBUS_ILLEGAL_DATA_VALUE = 0x03;
    public static final int MODBUS_FAILURE_IN_DEVICE = 0x04;
    public static final int MODBUS_ACKNOWLEDGE = 0x05;
    public static final int MODBUS_READ_HOLDING_REGISTERS_ERROR = 0x83;
    public static final int MODBUS_READ_INPUT_REGISTERS_ERROR = 0x84;
    //TODO: Finish modbus exceptions


    //Meter Names
    public static final String METER_CPP_1 = "cpp_1";

    //Parameter names - To Delete
    public static final String VOLT_PHASE_1_EL = "volt_phase_1_el";
    public static final String VOLT_PHASE_2_EL = "volt_phase_2_el";
    public static final String VOLT_PHASE_3_EL = "volt_phase_3_el";

    //Meter Models
    public static final int ELNET = 0;
    public static final int SATEC = 1;

    //Parameter Default frequencies - Checking thread will pass once every 1 min.
    public static final Integer EVERY_1_MIN = 1;
    public static final Integer EVERY_5_MIN = 5;
    public static final Integer EVERY_10_MIN = 10;
    public static final Integer EVERY_15_MIN = 15;
    public static final Integer EVERY_30_MIN = 30;
    public static final Integer EVERY_60_MIN = 60;
    public static final Integer EVERY_120_MIN = 120;

    //Max transactions to keep in memory
    public static final Integer MAX_NUMBER_TRANSACTIONS = 100;

    //Byte interpretation

    //modExceptions
    public static final String INCORRECT_RESPONSE_DATA = "Incorrect Response Data";

    //Modbus Positions

    //IdleTime, in seconds
    public static final int READER_IDLE_TIME = 20;
    public static final int WRITER_IDLE_TIME = 20;
    public static final int ALL_IDLE_TIME = 20;





    //Apps Exceptions
    public static final String CHANNEL_INIT = "Init Channel";
    public static final String FUTURE_CHANNEL_IS_NOT_DONE = "Future Channel is not Done";
    public static final String CHANNEL_UNSUCCESSFUL = "Future Channel is not Succesful!";
    public static final String CHANNEL_CLOSED = "Channel is closed!";
    public static final String CHANNEL_INACTIVE = "Channel is not Active!";


}
