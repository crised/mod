package scheme;


import java.net.InetSocketAddress;

/**
 * Created by crised on 7/9/14.
 */
public class Gateway {

    public final String name; //
    public final String host; // InetAddress.getByName(String host)
    public final int port;
    public final InetSocketAddress socketAddress;


    public Gateway(String name, String host, int port) {
        this.name = name;
        this.host = host;
        this.port = port;
        this.socketAddress = new InetSocketAddress(host,port);
    }

    public String getName() {
        return name;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public InetSocketAddress getSocketAddress() {
        return socketAddress;
    }
}
