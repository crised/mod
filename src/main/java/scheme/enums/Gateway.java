package scheme.enums;
import static utils.Consts.*;

/**
 * Created by crised on 7/17/14.
 */
public enum Gateway {

    /*AGROSUPER_NORTE("localhost"),
    TROMEN_1("localhost"),
    TROMEN_2("localhost");*/
    PRUEBA_CASA("192.168.1.15");

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
