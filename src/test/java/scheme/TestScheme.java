package scheme;

import aws.DynamoDbHelper;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by crised on 7/9/14.
 */
public class TestScheme {

    static final Logger LOG = LoggerFactory.getLogger("TestScheme");


    @Test
    public void testEnumBootstrap() throws  Exception{

        EnumBootstrap enumBootstrap = new EnumBootstrap();
        List<ModbusMessage> modbusMessagesList = enumBootstrap.getModbusMessagesList();
        LOG.info("finish");

    }





}
