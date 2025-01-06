package xyz.natefu.model.constantpool;

import xyz.natefu.ClassReader;
import xyz.natefu.model.IllegalByteCodeException;

import java.io.IOException;
import java.nio.ByteBuffer;

public final class ConstantPackage extends ConstantPoolInfo {
    short tag = ConstantPool.CONSTANT_Package;
    int nameIndex;

    public ConstantPackage(ClassReader reader) throws IOException {
        nameIndex = reader.readUnsignedShort();
    }

    public String toString() {
        return "Package\t\t[ nameIndex #" + nameIndex + " ]";
    }
}
