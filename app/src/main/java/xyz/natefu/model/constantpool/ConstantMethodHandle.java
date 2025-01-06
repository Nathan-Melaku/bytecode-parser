package xyz.natefu.model.constantpool;

import xyz.natefu.ClassReader;
import xyz.natefu.model.IllegalByteCodeException;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public final class ConstantMethodHandle extends ConstantPoolInfo {
    short tag = ConstantPool.CONSTANT_MethodHandle;
    int referenceKind;
    int referenceIndex;

    public ConstantMethodHandle(ClassReader reader) throws IllegalArgumentException, IOException {
        referenceKind = reader.readUnsignedByte();
        referenceIndex = reader.readUnsignedShort();
    }

    public String toString() {
        return "MethodHandle\t[ referenceKind " + referenceKind + ", referenceIndex #" + referenceIndex + " ]";
    }
}
