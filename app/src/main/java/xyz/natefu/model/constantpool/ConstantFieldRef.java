package xyz.natefu.model.constantpool;

import xyz.natefu.model.ConstantKind;
import java.nio.ByteBuffer;
import java.util.Arrays;

public final class ConstantFieldRef extends ConstantPoolInfo {
    short tag = ConstantKind.CONSTANT_FieldRef;
    short classIndex;
    short nameAndTypeIndex;

    public ConstantFieldRef(byte[] bytes) throws IllegalArgumentException {
        if (bytes.length != 4) {
            throw new IllegalArgumentException("4 bytes needed for a Fieldref constant");
        }

        var cIndex = ByteBuffer.wrap(Arrays.copyOfRange(bytes, 0, 2)).getShort();
        var ntIndex = ByteBuffer.wrap(Arrays.copyOfRange(bytes, 2, 4)).getShort();
        this.classIndex = cIndex;
        this.nameAndTypeIndex = ntIndex;
    }

    public String toString() {
        return "Fieldref\t\t[ classIndex #" + classIndex + ", nameandtypeindex #" + nameAndTypeIndex + " ]";
    }
}
