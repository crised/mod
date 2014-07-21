package aws;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import scheme.GroupMessage;
import scheme.Parameter;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by crised on 7/19/14.
 */
public class ItemBuilder {

    //Responses

    private List<Map<String, AttributeValue>> items;

    public ItemBuilder(GroupMessage groupMessage) {

        for (Parameter parameter : groupMessage.getParameterList()) {
            Map<String, AttributeValue> item = new HashMap<>();

            item.put("Id", new AttributeValue(parameter.getId()));
            item.put("TIMESTAMP", new AttributeValue(getTimeStamp()));
            item.put("StartReg", new AttributeValue().withN(String.valueOf(parameter.getStartRegister())));
            item.put("EndReg", new AttributeValue().withN(String.valueOf(parameter.getEndRegister())));
            item.put("FunctionCode", new AttributeValue().withB(ByteBuffer.allocate(1).put(parameter.getFunctionType())));
            item.put("Data", new AttributeValue().withB(parameter.getResponseBytes()));


        }

    }


    private String getTimeStamp() {
        //ISO8601 ms precision
        DateTime dt = new DateTime(DateTimeZone.UTC);
        DateTimeFormatter fmt = ISODateTimeFormat.basicDateTime();
        return fmt.print(dt);
    }

    public List<Map<String, AttributeValue>> getItems() {
        return items;
    }
}
