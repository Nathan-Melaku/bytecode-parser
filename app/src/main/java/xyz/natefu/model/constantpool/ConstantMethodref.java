package xyz.natefu.model.constantpool;

import java.util.Arrays;
import java.nio.ByteBuffer;

import xyz.natefu.model.ConstantKind;

public final class ConstantMethodref extends ConstantPoolInfo {
    short tag = ConstantKind.CONSTANT_MethodRef;
    short classIndex;
    short nameAndTypeIndex;

    public ConstantMethodref(byte[] bytes) throws IllegalArgumentException {
        if (bytes.length != 4) {
            throw new IllegalArgumentException("4 bytes needed for a Methodref constant");
        }

        var cIndex = ByteBuffer.wrap(Arrays.copyOfRange(bytes, 0, 2)).getShort();
        var ntIndex = ByteBuffer.wrap(Arrays.copyOfRange(bytes, 2, 4)).getShort();
        this.classIndex = cIndex;
        this.nameAndTypeIndex = ntIndex;
    }

    public String toString() {
        return "Methodref\t\t[ " +
               "classIndex #" + classIndex +
               ", nameAndtypeindex #" + nameAndTypeIndex + " ]";
    }
}
