package scheme.enums;

import static utils.Consts.*;

/**
 * Created by crised on 7/17/14.
 */
public enum Parameter  {

    //Elnet Registers

    VOLTAGE_LINE_1(1, 2, ELNET), VOLTAGE_LINE_2(3, 4, ELNET), VOLTAGE_LINE_3(5, 6, ELNET),
    CURRENT_LINE_1(13, 14, ELNET), CURRENT_LINE_2(15, 16, ELNET), APPARENT_POWER_LINE_1(19, 20, ELNET),

    // Satec Registers

    V1_VOLT_DEMAND(14592, 14593, SATEC), V2_VOLT_DEMAND(14594, 14595, SATEC), V3_VOLT_DEMAND(14596, 14597, SATEC);


    private Integer startReg;
    private Integer endReg;
    private Integer meterModel;
    private Byte functionCode;
    private Integer frequency;


    Parameter(int startReg, int endReg, int meterModel) throws ExceptionInInitializerError {
        if (startReg >= MODBUS_MAX_ADDRESS || endReg >= MODBUS_MAX_ADDRESS) throw new ExceptionInInitializerError("enum");
        this.startReg = startReg;
        this.endReg = endReg;
        this.meterModel = meterModel;
        this.functionCode = READ_INPUT_REGISTERS;
        this.frequency = EVERY_15_MIN;

    }

    Parameter(int startReg, int endReg, int meterModel, Byte functionCode, Integer frequency) throws Exception {
        this(startReg, endReg, meterModel);
        this.functionCode = functionCode;
        this.frequency = frequency;
    }

    public Integer getStartReg() {
        return startReg;
    }

    public Integer getEndReg() {
        return endReg;
    }

    public Integer getMeterModel() {
        return meterModel;
    }

    public Byte getFunctionCode() {
        return functionCode;
    }

    public Integer getFrequency() {
        return frequency;
    }
}
