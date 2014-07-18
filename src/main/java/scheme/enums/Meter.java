package scheme.enums;
import static utils.Consts.*;

/**
 * Created by crised on 7/17/14.
 */
public enum Meter {

    //name of enum = table name

    AGROSUPER_POLLOS((byte) 0x01, ELNET, Gateway.AGROSUPER_NORTE),
    TROMEN_ASERRADERO((byte) 0x01, ELNET, Gateway.TROMEN_1),
    TROMEN_ASERRIN((byte) 0x02, SATEC, Gateway.TROMEN_1),
    TROMEN_NORTE((byte) 0x01, ELNET, Gateway.TROMEN_2);

    private Byte modbusRtuAddress;
    private int meterModel;
    private Gateway gateway;

    Meter(Byte modbusRtuAddress, int meterModel, Gateway gateway) {
        this.modbusRtuAddress = modbusRtuAddress;
        this.meterModel = meterModel;
        this.gateway = gateway;
    }


    public Byte getModbusRtuAddress() {
        return modbusRtuAddress;
    }

    public int getMeterModel() {
        return meterModel;
    }

    public Gateway getGateway() {
        return gateway;
    }
}
