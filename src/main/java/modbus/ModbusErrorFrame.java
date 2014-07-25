package modbus;

import utils.AppException;

import java.nio.ByteBuffer;

/**
 * Created by crised on 7/18/14.
 */
public class ModbusErrorFrame extends Frame {

    private Byte errorCode; //Only in response.
    private Byte exceptionCode;


    //FCode 0x83 or 0x84 (0x03 or 0x04)
    //Exception Code - 1 Byte (0x01, 0x02, 0x03,0x04)
    public ModbusErrorFrame(ByteBuffer in) throws Exception{

        try{
            in.position(0);
            this.errorCode = in.get();
            this.exceptionCode = in.get();
        } catch (IndexOutOfBoundsException e){
            throw new AppException("Bad Incoming Data");
        }

    }

    public Byte getErrorCode() {
        return errorCode;
    }

    public Byte getExceptionCode() {
        return exceptionCode;
    }
}
