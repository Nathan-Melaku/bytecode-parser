package xyz.natefu.model.constantpool;

import xyz.natefu.ClassReader;

import java.io.IOException;

public final class ConstantUtf8 extends ConstantPoolInfo {
    short tag = ConstantPool.CONSTANT_Utf8;
    String data;

    public ConstantUtf8(ClassReader reader) throws IOException {
        this.data = reader.readUTF();
    }

    public String getData() {
        return data;
    }

    public String toString() {
        return "Utf8\t\t\t[ " + data + " ]";
    }
}
