package xyz.natefu.model.constantpool;

import xyz.natefu.ClassReader;

import java.io.IOException;

public final class ConstantDouble extends ConstantPoolInfo {

    short tag = ConstantPool.CONSTANT_Double;
    double data;

    public ConstantDouble(ClassReader reader) throws IOException {
        this.data = reader.readDouble();
    }

    public String toString() {
        return "Double\t\t\t[ " + data + " ]";
    }
}
