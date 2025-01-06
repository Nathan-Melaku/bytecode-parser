package xyz.natefu.model.constantpool;

import xyz.natefu.ClassReader;

import java.io.IOException;

public final class ConstantLong extends ConstantPoolInfo {

    short tag = ConstantPool.CONSTANT_Long;
    long data;

    public ConstantLong(ClassReader reader) throws IOException {
        this.data = reader.readLong();
    }

    public String toString() {
        return "Long\t\t\t[ " + data + " ]";
    }
}
