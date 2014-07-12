package scheme;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by crised on 7/9/14.
 */
public class SchemeBootstrap {

    private final List<Gateway> gatewayList = new ArrayList<>();
    private final Parameters parameters = new Parameters();

    //Gateways
    private Gateway gateway1;

    //Meters
    private final List<Meter> meterList1 = new ArrayList<>();

    public SchemeBootstrap() {

        initMeters();
        initGateways();

        //Fill gatewayList
        gatewayList.add(gateway1);

    }

    private void initGateways() {

        gateway1 = new Gateway("agrosuper_1", "localhost", 8000, meterList1);


    }

    private void initMeters() {

        //AgrosuperMeters
        Meter meter1 = new Meter("agrosuper_norte_1", 0x05, parameters.getElnetList1());

        //Now add to meter list
        meterList1.add(meter1);

    }

    public List<Gateway> getGatewayList() {
        return gatewayList;
    }
}
