package xyz.natefu.model.constantpool;

import java.nio.ByteBuffer;
import xyz.natefu.model.ConstantKind;

public final class ConstantString extends ConstantPoolInfo {
    short tag = ConstantKind.CONSTANT_String;
    short stringIndex;

    public ConstantString(byte[] bytes) throws IllegalArgumentException {
        if (bytes.length != 2) {
            throw new IllegalArgumentException();
        }

        stringIndex = ByteBuffer.wrap(bytes).getShort();
    }

    public short getStringIndex() {
        return stringIndex;
    }

    public String toString() {
        return "String\t\t\t[ #" + stringIndex + " ]";
    }
}
