package scheme;

import netty.NettyHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.Collection;

import static utils.Consts.groupMessageMap;

/**
 * Created by crised on 7/21/14.
 */
public class Tests {

    static final Logger LOG = LoggerFactory.getLogger("Tests");

    @Test
    public void test1() throws Exception{

        EnumBootstrap appData = new EnumBootstrap();
        NettyHelper nettyHelper = new NettyHelper(appData.getGatewayList());
        Collection<GroupMessage> groupMessages = groupMessageMap.values();

        LOG.info("");



    }


}
