package scheme;

import java.util.List;

/**
 * Created by crised on 7/9/14.
 */
public class Meter {

    public final String tableName;
    public final Integer modbusRtuAddress;
    public final List<Parameter> parameters;

    public Meter(String tableName, Integer modbusRtuAddress, List<Parameter> parameters) {
        this.tableName = tableName;
        this.modbusRtuAddress = modbusRtuAddress;
        this.parameters = parameters;
    }
}
