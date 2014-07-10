package aws;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ModException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * Created by crised on 7/10/14.
 */
public class DynamoDbHelper {

    static final Logger LOG = LoggerFactory.getLogger("DynamoDbHelper");


    public AmazonDynamoDBAsyncClient getDynamoDB() throws Exception {

        AWSCredentials credentials = null;
        AmazonDynamoDBAsyncClient dynamoDB = null;

        try {
            credentials = new ProfileCredentialsProvider().getCredentials();
        } catch (Exception e) {
            throw new ModException("Cannot load the credentials, location (~/.aws/credentials)", e);
        }
        dynamoDB = new AmazonDynamoDBAsyncClient(credentials); //asynchronous
        Region usEast1 = Region.getRegion(Regions.US_EAST_1);
        dynamoDB.setRegion(usEast1);
        return dynamoDB;

    }

    public Map<String, AttributeValue> newItem(String Id, Float Value){

        return  null;

    }

    public Map<String, AttributeValue> newItem(String Id, Integer Value){

        return  null;

    }

    public String getTimeStamp(){

        //ISO8601 ms precision
        DateTime dt = new DateTime(DateTimeZone.UTC);
        DateTimeFormatter fmt = ISODateTimeFormat.basicDateTime();
        return fmt.print(dt);

    }


}
