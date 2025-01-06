package xyz.natefu.model.constantpool;

import xyz.natefu.ClassReader;
import xyz.natefu.model.IllegalByteCodeException;

import java.io.IOException;
import java.nio.ByteBuffer;

public final class ConstantModule extends ConstantPoolInfo {
    short tag = ConstantPool.CONSTANT_Module;
    int nameIndex;

    public ConstantModule(ClassReader reader) throws IOException {
        nameIndex = reader.readUnsignedShort();
    }

    public String toString() {
        return "Module [ nameIndex #" + nameIndex + " ]";
    }
}
