package main;

import modbus.MessageBuilder;
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


    public static void main(String args[]) {

        final AppHelper appHelper = new AppHelper();

        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


        scheduler.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {

                LOG.info("run");
                if (appHelper == null) LOG.info("null");

                for (Gateway gateway : appHelper.getGateways()) {
                    for (Meter meter : gateway.getMeters())
                        for (Parameter parameter : meter.getParameters()) {
                            appHelper.getNettyHelper().sendMessagesTo(gateway, MessageBuilder.ModbusMessage(meter, parameter));
                        }
                }

            }
        }, 0, 10, TimeUnit.SECONDS);

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                System.out.println("hello");
            }
        }));


        LOG.info("finish main");

    }


}
