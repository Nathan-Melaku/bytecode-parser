package xyz.natefu.model.constantpool;

import xyz.natefu.ClassReader;
import xyz.natefu.model.IllegalByteCodeException;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public final class ConstantDynamic extends ConstantPoolInfo {
    short tag = ConstantPool.CONSTANT_Dynamic;
    int bootStrapMethodAttrIndex;
    int nameAndTypeIndex;

    public ConstantDynamic(ClassReader reader) throws IOException {

        bootStrapMethodAttrIndex = reader.readUnsignedShort();
        nameAndTypeIndex = reader.readUnsignedShort();
    }

    public String toString() {
        return "Dynamic\t\t\t[ " + "bootStrapMethodAttrIndex #" + bootStrapMethodAttrIndex +
               ", nameAndTypeIndex #" + nameAndTypeIndex + " ]";
    }
}
