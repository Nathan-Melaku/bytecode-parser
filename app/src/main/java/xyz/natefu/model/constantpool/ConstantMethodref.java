package xyz.natefu.model.constantpool;

import java.io.IOException;
import java.util.Arrays;
import java.nio.ByteBuffer;

import xyz.natefu.ClassReader;
import xyz.natefu.model.IllegalByteCodeException;

public final class ConstantMethodref extends ConstantPoolInfo {
    short tag = ConstantPool.CONSTANT_MethodRef;
    int classIndex;
    int nameAndTypeIndex;

    public ConstantMethodref(ClassReader reader) throws IOException {
        this.classIndex = reader.readUnsignedShort();
        this.nameAndTypeIndex = reader.readUnsignedShort();
    }

    public String toString() {
        return "Methodref\t\t[ " +
               "classIndex #" + classIndex +
               ", nameAndTypeIndex #" + nameAndTypeIndex + " ]";
    }
}
