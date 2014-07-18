package main;

import com.google.common.primitives.Bytes;
import com.google.common.primitives.Shorts;
import netty.NettyHelper;
import scheme.Gateway;
import scheme.ModbusMessage;
import scheme.Parameter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utils.Consts.*;

/**
 * Created by crised on 7/12/14.
 */
public class AppHelper  {
/*
    private List<Gateway> gateways;
    private NettyHelper nettyHelper;

    private static short trans_id;
    private final static Map<Short, ModbusMessage> transMap = new HashMap<>();

    public AppHelper() {

        SchemeBootstrap schemeBootstrap = new SchemeBootstrap();
        gateways = schemeBootstrap.getGatewayList();
        nettyHelper = new NettyHelper(gateways);

    }

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

    public List<Gateway> getGateways() {
        return gateways;
    }



    public NettyHelper getNettyHelper() {
        return nettyHelper;
    }*/
}
