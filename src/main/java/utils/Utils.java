package utils;

import com.google.common.primitives.Bytes;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * Created by crised on 7/10/14.
 */
public class Utils {

    //Little endian. modbus is a "big-endian" protocol: that is, the more significant byte of a 16-bit value is sent before the less significant byte.

    public static ByteBuffer getByteBufferFromRemainingBytes(ByteBuffer buffer){

        byte[] b = new byte[buffer.remaining()];
        buffer.get(b);
        return ByteBuffer.wrap(b);

    }



}
