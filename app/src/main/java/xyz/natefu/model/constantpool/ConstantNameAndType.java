package xyz.natefu.model.constantpool;

import xyz.natefu.ClassReader;
import xyz.natefu.model.IllegalByteCodeException;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public final class ConstantNameAndType extends ConstantPoolInfo {
    short tag = ConstantPool.CONSTANT_NameAndType;
    int nameIndex;
    int descriptorIndex;

    public ConstantNameAndType(ClassReader reader) throws IOException {
        this.nameIndex = reader.readUnsignedShort();
        this.descriptorIndex = reader.readUnsignedShort();
    }

    public String toString() {
        return "Nameandtype\t\t[ " +
               "nameIndex #" + nameIndex +
               ", decriptorIndex #" + descriptorIndex + " ]";
    }
}
