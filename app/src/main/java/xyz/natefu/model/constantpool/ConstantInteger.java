package xyz.natefu.model.constantpool;

import xyz.natefu.ClassReader;

import java.io.IOException;

final class ConstantInteger extends ConstantPoolInfo {
    short tag = ConstantPool.CONSTANT_Integer;
    int data;

    ConstantInteger(ClassReader reader) throws IOException {
        this.data = reader.readInt();
    }

    public String toString() {
        return "Integer\t\t\t[ " + data + " ]";
    }
}
