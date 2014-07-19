package main;

import netty.NettyHelper;
import scheme.EnumBootstrap;
import scheme.GroupMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by crised on 7/12/14.
 */
public class AppHelper {
    private NettyHelper nettyHelper;

    private final static short trans_id = 0;
    private final static Map<Short, GroupMessage> transMap = new HashMap<>();
    //Once stored in DynamoDb, it's removed.
    //Only "GOOD" GroupMessage should be stored here, if there is an error, they should be removed.

    public AppHelper() throws Exception {

        EnumBootstrap enumBootstrap = new EnumBootstrap();
        nettyHelper = new NettyHelper(enumBootstrap.getGatewayList());

    }
/*
    //When writing modbus Packet
    public List<Byte> getTransactionId(Parameter param) {

        if (trans_id >= MAX_NUMBER_TRANSACTIONS) trans_id = 0;

        //Transaction begins at 1.
        trans_id++;
        transMap.put(trans_id, param);
        return Bytes.asList(Shorts.toByteArray(trans_id)).subList(0,2); //Only 2 Bytes

    }

    public Parameter getParamFromTransaction(List<Byte> transBytes) {

        Short trans;
        trans = Shorts.fromByteArray(Bytes.toArray(transBytes));
        return transMap.get(trans); //null if no mapping
    }
*/
}

