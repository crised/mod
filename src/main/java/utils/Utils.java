package utils;

import com.google.common.primitives.Bytes;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * Created by crised on 7/10/14.
 */
public class Utils {

    //Little endian. modbus is a "big-endian" protocol: that is, the more significant byte of a 16-bit value is sent before the less significant byte.
    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();


    public static ByteBuffer getByteBufferFromRemainingBytes(ByteBuffer buffer){

        byte[] b = new byte[buffer.remaining()];
        buffer.get(b);
        return ByteBuffer.wrap(b);

    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String("0x" + hexChars);
    }


}
