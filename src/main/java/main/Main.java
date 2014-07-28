package main;

import aws.DynamoDb;
import netty.NettyHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scheme.*;

import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static utils.Consts.groupMessageMap;

/**
 * Created by crised on 7/9/14.
 */
public class Main {

    static final Logger LOG = LoggerFactory.getLogger("Main");

    static EnumBootstrap appData;
    static NettyHelper nettyHelper;
    static Collection<GroupMessage> groupMessages;
    static ScheduledExecutorService scheduler;
    static DynamoDb dynamoDb;


    public static void main(String args[]) throws Exception {

        init();

        scheduler.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                try {
                    for (GroupMessage groupMessage : groupMessages) {
                        if (groupMessage.isMyTurn()) {
                            nettyHelper.sendOneGroupMessage(groupMessage);
                            dynamoDb.putItems();
                        }
                    }
                } catch (Exception e) {
                    LOG.error(e.getCause().getMessage());

                }
            }
        }, 0, 1, TimeUnit.SECONDS);

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                nettyHelper.shutDown();
                System.out.println("finished");
            }
        }));

        LOG.info("finish main");
    }

    private static void init() throws Exception {

        appData = new EnumBootstrap();
        nettyHelper = new NettyHelper(appData.getGatewayList());
        groupMessages = groupMessageMap.values();
        scheduler = Executors.newScheduledThreadPool(1);
        dynamoDb = new DynamoDb();

    }
}




