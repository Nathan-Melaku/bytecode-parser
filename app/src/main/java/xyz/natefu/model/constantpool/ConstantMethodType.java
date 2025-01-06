package xyz.natefu.model.constantpool;

import xyz.natefu.ClassReader;
import xyz.natefu.model.IllegalByteCodeException;

import java.io.IOException;
import java.nio.ByteBuffer;

public final class ConstantMethodType extends ConstantPoolInfo {

    short tag = ConstantPool.CONSTANT_MethodType;
    int descriptorIndex;

    public ConstantMethodType(ClassReader reader) throws IOException {
        descriptorIndex = reader.readUnsignedShort();
    }

    public String toString() {
        return "MethodType \t[ " + "descriptorindex #" + descriptorIndex + " ]";
    }
}
