package aws;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scheme.GroupMessage;
import scheme.Parameter;
import utils.AppException;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

import static utils.Consts.*;


/**
 * Created by crised on 7/10/14.
 */
public class DynamoDb {

    static final Logger LOG = LoggerFactory.getLogger("DynamoDb");
    ListIterator<GroupMessage> iterator;
    AmazonDynamoDBAsyncClient dynamoDB;

    public DynamoDb() throws Exception {

        AWSCredentials credentials = null;
        AmazonDynamoDBAsyncClient dynamoDB = null;

        try {
            credentials = new ProfileCredentialsProvider().getCredentials();
        } catch (Exception e) {
            throw new AppException("Cannot load the credentials, location (~/.aws/credentials)", e);
        }
        dynamoDB = new AmazonDynamoDBAsyncClient(credentials); //asynchronous
        Region usEast1 = Region.getRegion(Regions.US_EAST_1);
        dynamoDB.setRegion(usEast1);
    }

    private void init(){

    }


    public void putItems() {

        try {

            if (groupMessageQueue.size() == 0) return;

            for (iterator = groupMessageQueue.listIterator(); iterator.hasNext(); ) {

                GroupMessage groupMessage = iterator.next();

                for (Parameter parameter : groupMessage.getParameterList()) {

                    Map<String, AttributeValue> item = new HashMap<>();
                    item.put("Id", new AttributeValue(parameter.getId()));
                    item.put("TIMESTAMP", new AttributeValue(getTimeStamp()));
                    item.put("StartReg", new AttributeValue().withN(String.valueOf(parameter.getStartRegister())));
                    item.put("EndReg", new AttributeValue().withN(String.valueOf(parameter.getEndRegister())));
                    item.put("FunctionCode", new AttributeValue().withB(ByteBuffer.allocate(1).put(parameter.getFunctionType())));
                    item.put("Data", new AttributeValue().withB(parameter.getResponseBytes()));

                    PutItemRequest putItemRequest = new PutItemRequest(groupMessage.getMeter().getTableName(), item);
                    PutItemResult putItemResult = dynamoDB.putItem(putItemRequest);

                }
            }


        } catch (AmazonServiceException ase) {
            LOG.error("Caught an AmazonServiceException, which means your request made it "
                    + "to AWS, but was rejected with an error response for some reason.");
            LOG.error("Error Message:    " + ase.getMessage());
            LOG.error("HTTP Status Code: " + ase.getStatusCode());
            LOG.error("AWS Error Code:   " + ase.getErrorCode());
            LOG.error("Error Type:       " + ase.getErrorType());
            LOG.error("Request ID:       " + ase.getRequestId());
            dynamoDB = null;
        } catch (AmazonClientException ace) {
            LOG.error("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with AWS, "
                    + "such as not being able to access the network.");
            LOG.error("Error Message: " + ace.getMessage());
            dynamoDB = null;


        } catch (Exception e) {
            iterator.remove();
            dynamoDB = null;

        }
    }


    private String getTimeStamp() {
        //ISO8601 ms precision
        DateTime dt = new DateTime(DateTimeZone.UTC);
        DateTimeFormatter fmt = ISODateTimeFormat.basicDateTime();
        return fmt.print(dt);
    }
}
