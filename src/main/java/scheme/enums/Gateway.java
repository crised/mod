package scheme.enums;
import static utils.Consts.*;

/**
 * Created by crised on 7/17/14.
 */
public enum Gateway {

    AGROSUPER_NORTE("192.168.1.15"),
    TROMEN_1("192.168.1.11"),
    TROMEN_2("localhost");

    private String host; // InetAddress.getByName(String host)
    private int port;

    Gateway(String host) {
        this.host = host;
        this.port = MODBUS_TCP_PORT;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
