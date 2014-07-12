package main;

import netty.NettyHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crised on 7/9/14.
 */
public class Main {

    static final Logger LOG = LoggerFactory.getLogger("Main");


    public static void main(String args[]) {

        NettyHelper nettyHelper = new NettyHelper();

        nettyHelper.shutDown();




    }
}
