package scheme.enums;
import static utils.Consts.*;

/**
 * Created by crised on 7/17/14.
 */
public enum Meter {

    //name of enum = table name - IncludeModel!

    AGROSUPER_POLLOS_ELNET_LT((byte) 0x01, ELNET, Gateway.AGROSUPER_NORTE),
    TROMEN_ASERRADERO_ELNET_LT((byte) 0x01, ELNET, Gateway.TROMEN_1),
    TROMEN_ASERRIN_SATEC_PM_130((byte) 0x02, SATEC, Gateway.TROMEN_1),
    TROMEN_NORTE_ELNET_LT((byte) 0x01, ELNET, Gateway.TROMEN_2);

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
