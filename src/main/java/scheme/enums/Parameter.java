package scheme.enums;

import static utils.Consts.*;

/**
 * Created by crised on 7/17/14.
 */
public enum Parameter {

    //Elnet Registers

    VOLTAGE_LINE_1(1, 2, ELNET), VOLTAGE_LINE_2(3, 4, ELNET), VOLTAGE_LINE_3(5, 6, ELNET),
    CURRENT_LINE_1(13, 14, ELNET), CURRENT_LINE_2(15, 16, ELNET), APPARENT_POWER_LINE_1(19, 20, ELNET),

    // Satec Registers

    V1_VOLT_DEMAND(14592, 14593, SATEC), V2_VOLT_DEMAND(14594, 14595, SATEC), V3_VOLT_DEMAND(14596, 14597, SATEC);


    private int startReg;
    private int endReg;
    private int meterModel;
    private Byte functionCode;
    private int frequency;

    Parameter(int startReg, int endReg, int meterModel) {
        this.startReg = startReg;
        this.endReg = endReg;
        this.meterModel = meterModel;
        this.functionCode = READ_INPUT_REGISTERS;
        this.frequency = EVERY_15_MIN;

    }

    Parameter(int startReg, int endReg, int meterModel, Byte functionCode, int frequency) {
        this.startReg = startReg;
        this.endReg = endReg;
        this.meterModel = meterModel;
        this.functionCode = functionCode;
        this.frequency = frequency;
    }

    public int getStartReg() {
        return startReg;
    }

    public int getEndReg() {
        return endReg;
    }

    public int getMeterModel() {
        return meterModel;
    }

    public Byte getFunctionCode() {
        return functionCode;
    }

    public int getFrequency() {
        return frequency;
    }
}
