package xyz.natefu.model.constantpool;

import xyz.natefu.ClassReader;
import xyz.natefu.model.IllegalByteCodeException;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public final class ConstantInvokeDynamic extends ConstantPoolInfo {
    short tag = ConstantPool.CONSTANT_InvokeDynamic;
    int bootStrapMethodAttrIndex;
    int nameAndTypeIndex;

    public ConstantInvokeDynamic(ClassReader reader) throws IOException {
        this.bootStrapMethodAttrIndex = reader.readUnsignedShort();
        this.nameAndTypeIndex = reader.readUnsignedShort();
    }

    public String toString() {
        return "InvokeDynamic \t[ " + "bootStrapMethodAttrIndex #" + bootStrapMethodAttrIndex +
               ", nameAndTypeIndex #" + nameAndTypeIndex + " ]";
    }
}
