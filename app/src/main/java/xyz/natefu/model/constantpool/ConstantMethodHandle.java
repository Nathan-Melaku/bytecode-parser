package xyz.natefu.model.constantpool;

import xyz.natefu.model.IllegalByteCodeException;

import java.nio.ByteBuffer;
import java.util.Arrays;

public final class ConstantMethodHandle extends ConstantPoolInfo {
    short tag = ConstantKind.CONSTANT_MethodHandle;
    byte referenceKind;
    short referenceIndex;

    public ConstantMethodHandle(byte[] bytes) throws IllegalArgumentException {
        if (bytes.length != 3) {
            throw new IllegalByteCodeException("Bad MethodHandle constant");
        }

        referenceKind = bytes[0];
        referenceIndex = ByteBuffer.wrap(Arrays.copyOfRange(bytes, 1, 3)).getShort();
    }

    public String toString() {
        return "MethodHandle\t[ referencekind " + referenceKind + ", referenceindex #" + referenceIndex + " ]";
    }
}
