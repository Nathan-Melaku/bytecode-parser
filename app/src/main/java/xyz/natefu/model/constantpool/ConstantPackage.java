package xyz.natefu.model.constantpool;

import xyz.natefu.model.ConstantKind;
import java.nio.ByteBuffer;

public final class ConstantPackage extends ConstantPoolInfo {
    short tag = ConstantKind.CONSTANT_Package;
    short nameIndex;

    public ConstantPackage(byte[] bytes) {
        if (bytes.length != 2) {
            throw new IllegalArgumentException();
        }

        nameIndex = ByteBuffer.wrap(bytes).getShort();
    }

    public String toString() {
        return "Package\t\t[ nameIndex #" + nameIndex + " ]";
    }
}
