package xyz.natefu.model.attributes;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class ExceptionEntry {
    final short startPc;
    final short endPc;
    final short handlerPc;
    final short catchPc;

    public ExceptionEntry(byte[] bytes) {
        assert bytes.length == 8;
        this.startPc = ByteBuffer.wrap(Arrays.copyOfRange(bytes, 0, 2)).getShort();
        this.endPc = ByteBuffer.wrap(Arrays.copyOfRange(bytes, 2, 4)).getShort();
        this.handlerPc = ByteBuffer.wrap(Arrays.copyOfRange(bytes, 4, 6)).getShort();
        this.catchPc = ByteBuffer.wrap(Arrays.copyOfRange(bytes, 6, 8)).getShort();
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        sb.append("\t\t\tstartPc: ").append(this.startPc).append("\n")
                .append("\t\t\tendPc: ").append(this.endPc).append("\n")
                .append("\t\t\thandlerPc: ").append(this.handlerPc).append("\n")
                .append("\t\t\tcatchPc: ").append(this.catchPc).append("\n");
        return sb.toString();
    }
}
