package main;

import modbus.ModbusFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scheme.Gateway;
import scheme.Meter;
import scheme.Parameter;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by crised on 7/9/14.
 */
public class Main {

    static final Logger LOG = LoggerFactory.getLogger("Main");


    public static void main(String args[]) throws Exception{

        /*
        final AppHelper appHelper = new AppHelper();

        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


        //TODO: Initialize ModbusMessage here, to see an Exception if any at the beginning.
        //TODO: Get this logic Helper Method
        //TODO: Fix transactionid
        scheduler.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {

                LOG.info("run");
                if (appHelper == null) LOG.info("null");

                try {

                    for (Gateway gateway : appHelper.getGateways()) {
                        for (Meter meter : gateway.getMeters())
                            for (Parameter parameter : meter.getParameters()) {
                                ModbusFrame modbusFrame = new ModbusFrame(meter, parameter);
                                appHelper.getNettyHelper().sendMessagesTo(gateway, modbusFrame.getMessageBytes());
                            }
                    }

                } catch (Exception e) {
                    LOG.error(e.getMessage());
                }


            }
        }, 0, 10, TimeUnit.SECONDS);

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                appHelper.getNettyHelper().shutDown();
            }
        }));


        LOG.info("finish main");*/

    }


}
