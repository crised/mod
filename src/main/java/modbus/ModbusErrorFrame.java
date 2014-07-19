package modbus;

import java.util.List;

/**
 * Created by crised on 7/18/14.
 */
public class ModbusErrorFrame extends Frame {

    //FCode 0x83 or 0x84 (0x03 or 0x04)
    //Exception Code - 1 Byte (0x01, 0x02, 0x03,0x04)
    public ModbusErrorFrame(List<Byte> incomingBytes) {
    }
}
