package xyz.natefu.model.constantpool;

import xyz.natefu.ClassReader;
import xyz.natefu.model.IllegalByteCodeException;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public final class ConstantInterfaceMethodRef extends ConstantPoolInfo {
    short tag = ConstantPool.CONSTANT_InterfaceMethodRef;
    int nameIndex;
    int nameAndTypeIndex;

    public ConstantInterfaceMethodRef(ClassReader reader) throws IOException {

        this.nameIndex = reader.readUnsignedShort();
        this.nameAndTypeIndex = reader.readUnsignedShort();
    }

    public String toString() {
        return "InterfaceMethodRef\t[ nameIndex #" + nameIndex + ", nameAndTypeIndex #" + nameAndTypeIndex + " ]";
    }
}
