package scheme;

import java.util.List;

/**
 * Created by crised on 7/9/14.
 */
public class Meter {

    public final String tableName;
    public final Byte modbusRtuAddress;
    public final List<Parameter> parameters;


    public Meter(String tableName, Byte modbusRtuAddress, List<Parameter> parameters) {
        this.tableName = tableName;
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
}
