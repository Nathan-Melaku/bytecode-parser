package xyz.natefu.model.constantpool;

import xyz.natefu.model.ConstantKind;
import java.nio.ByteBuffer;
import java.util.Arrays;

public final class ConstantDynamic extends ConstantPoolInfo {
    short tag = ConstantKind.CONSTANT_Dynamic;
    short bootStrapMethodAttrIndex;
    short nameAndTypeIndex;

    public ConstantDynamic(byte[] bytes){
        if (bytes.length != 4) {
            throw new IllegalArgumentException();
        }

        bootStrapMethodAttrIndex = ByteBuffer.wrap(Arrays.copyOfRange(bytes, 0, 2)).getShort();
        nameAndTypeIndex = ByteBuffer.wrap(Arrays.copyOfRange(bytes, 2, 4)).getShort();
    }

    public String toString() {
        return "Dynamic\t\t\t[ " + "bootstrapmethodattrindex #" + bootStrapMethodAttrIndex +
               ", nameandtypeindex #" + nameAndTypeIndex + " ]";
    }
}
