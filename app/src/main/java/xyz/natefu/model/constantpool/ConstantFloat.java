package xyz.natefu.model.constantpool;

import xyz.natefu.ClassReader;
import xyz.natefu.model.IllegalByteCodeException;

import java.io.IOException;
import java.nio.ByteBuffer;

public final class ConstantFloat extends ConstantPoolInfo {
    short tag = ConstantPool.CONSTANT_Float;
    float data;

    public ConstantFloat(ClassReader reader) throws IOException {
        this.data = reader.readFloat();
    }

    public String toString() {
        return "Float\t\t\t[ " + data + " ]";
    }
}
