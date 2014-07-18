package scheme;

import java.util.List;

/**
 * Created by crised on 7/9/14.
 */
public class Meter {

    private final String tableName;
    private final Gateway gateway;
    private final Byte modbusRtuAddress;
    private final List<Parameter> parameters;


    public Meter(String tableName, Gateway gateway, Byte modbusRtuAddress, List<Parameter> parameters) {
        this.tableName = tableName;
        this.gateway = gateway;
        this.modbusRtuAddress = modbusRtuAddress;
        this.parameters = parameters;
    }

    public String getTableName() {
        return tableName;
    }

    public Byte getModbusRtuAddress() {
        return modbusRtuAddress;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public Gateway getGateway() {
        return gateway;
    }
}
