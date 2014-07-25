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

    public static String bytesToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        sb.append("0x");
        for(byte b: a)
            sb.append(String.format("%02X", b & 0xff));
        return sb.toString();
    }


}
