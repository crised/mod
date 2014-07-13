package main;

import io.netty.bootstrap.Bootstrap;
import netty.NettyHelper;
import scheme.Gateway;
import scheme.Meter;
import scheme.Parameter;
import scheme.SchemeBootstrap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by crised on 7/12/14.
 */
public class AppHelper  {

    private List<Gateway> gateways;
    private NettyHelper nettyHelper;



    public AppHelper() {

        SchemeBootstrap schemeBootstrap = new SchemeBootstrap();
        gateways = schemeBootstrap.getGatewayList();
        nettyHelper = new NettyHelper(gateways);

    }

    public List<Gateway> getGateways() {
        return gateways;
    }

    public NettyHelper getNettyHelper() {
        return nettyHelper;
    }
}
