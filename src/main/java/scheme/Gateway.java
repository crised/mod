package scheme;

import java.net.InetAddress;
import java.util.List;

/**
 * Created by crised on 7/9/14.
 */
public class Gateway {

    public final String name; //table_name
    public final String host; // InetAddress.getByName(String host)
    public final int port;
    public final List<Meter> meters;


    public Gateway(String name, String host, int port, List<Meter> meters) {
        this.name = name;
        this.host = host;
        this.port = port;
        this.meters = meters;
    }


}
