package scheme;

import com.google.common.primitives.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * Created by crised on 7/9/14.
 */
public class TestScheme {

    static final Logger LOG = LoggerFactory.getLogger("TestScheme");

    @Test
    public void testEnumBootstrap() throws Exception {

        EnumBootstrap enumBootstrap = new EnumBootstrap();
        List<GroupMessage> groupMessagesList = enumBootstrap.getGroupMessagesList();
        for (Meter meter : enumBootstrap.getMeterList()) {
            int size = 0;
            for (GroupMessage groupMessage : groupMessagesList) {
                if (groupMessage.getMeter() == meter)
                    size = size + groupMessage.getParameterList().size();
            }
            Assert.assertEquals(meter.getParameters().size(), size, "Size of Meters Params don't match");
        }
    }

    @Test
    private void complementTwo(){

        int test = UnsignedBytes.toInt((byte) 0xFF);
        long test2 = UnsignedInts.toLong(0xFFFF);

        Integer maxModbusRegister = 65535;

        byte[] bytes = ByteBuffer.allocate(2).put(Ints.toByteArray(maxModbusRegister),2,2).array();// {-1, -1} == 0xFFFF

        LOG.info("");




    }


}
