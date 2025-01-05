package xyz.natefu.model.constantpool;

import xyz.natefu.model.IllegalByteCodeException;

import java.nio.ByteBuffer;
import java.util.Arrays;

public final class ConstantNameAndType extends ConstantPoolInfo {
    short tag = ConstantKind.CONSTANT_NameAndType;
    short nameIndex;
    short descriptorIndex;

    public ConstantNameAndType(byte[] bytes) throws IllegalArgumentException {
        if (bytes.length != 4) {
            throw new IllegalByteCodeException("4 bytes needed for a NameType constant");
        }

        var nIndex = ByteBuffer.wrap(Arrays.copyOfRange(bytes, 0, 2)).getShort();
        var dIndex = ByteBuffer.wrap(Arrays.copyOfRange(bytes, 2, 4)).getShort();
        this.nameIndex = nIndex;
        this.descriptorIndex = dIndex;
    }

    public String toString() {
        return "Nameandtype\t\t[ " +
               "nameIndex #" + nameIndex +
               ", decriptorIndex #" + descriptorIndex + " ]";
    }
}
