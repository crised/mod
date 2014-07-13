package main;

import netty.NettyHelper;
import scheme.Gateway;
import scheme.SchemeBootstrap;

import java.util.List;

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
