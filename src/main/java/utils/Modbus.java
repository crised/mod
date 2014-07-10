package utils;

import com.google.common.primitives.Bytes;
import com.google.common.primitives.Shorts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by crised on 7/10/14.
 */
public class Modbus {

    private static short trans_id;
    private final static Map<Short, String> transMap = new HashMap<>();

    //When writing Modbus Packet
    public static List<Byte> getTransactionId(String paramId) {

        if (trans_id == Short.MAX_VALUE) trans_id = 0;

        //Transaction begins at 1.
        trans_id++;
        transMap.put(trans_id, paramId);
        return Bytes.asList(Shorts.toByteArray(trans_id));

    }

    public static String getParamNameFromTransaction(List<Byte> transBytes) {

        Short trans;
        trans = Shorts.fromByteArray(Bytes.toArray(transBytes));
        return transMap.get(trans); //null if no mapping
    }

}
