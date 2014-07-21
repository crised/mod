package modbus;

import com.google.common.primitives.Bytes;
import com.google.common.primitives.Ints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ModException;
import utils.Utils;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * Created by crised on 7/18/14.
 */
public class ModbusResponseFrame extends Frame {

    static final Logger LOG = LoggerFactory.getLogger("ModbusResponseFrame");

    private Byte byteCount; //Only in response.



    public ModbusResponseFrame(ByteBuffer in) throws Exception {

        //{transId, TransId, ProtocolId, ProtocolId,
        //{length, length, unitId, fCode,
        //{byte1_1, byte2_1, byte1_2, byte2_2... n x2.

        //ByteBuffer.wrap(in.array(), 4, 2);

        try {
            in.position(0);
            this.transId = ByteBuffer.allocate(2).put(in.get()).put(in.get());

            if (this.protocolId.array() !=
                    ByteBuffer.allocate(2).put(in.get()).put(in.get()).array())
                throw new ModException("Bad Incoming Data");

            this.length = ByteBuffer.allocate(2).put(in.get()).put(in.get());
            this.unitId = in.get();
            this.fCode = in.get();
            this.byteCount = in.get(); //Only in response Frame.
            if (in.remaining() % 2 != 0) throw new ModException("Register Value Should be Pair");
            this.data = Utils.getByteBufferFromRemainingBytes(in);
        } catch (RuntimeException e) {
            LOG.error(e.getMessage());
            throw new ModException(e.getMessage());
        }
    }

    public Byte getByteCount() {
        return byteCount;
    }
}
