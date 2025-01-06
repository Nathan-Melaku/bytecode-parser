package xyz.natefu.model.constantpool;

import xyz.natefu.ClassReader;

import java.io.IOException;

public final class ConstantString extends ConstantPoolInfo {
    short tag = ConstantPool.CONSTANT_String;
    int stringIndex;

    public ConstantString(ClassReader reader) throws IOException {
        this.stringIndex = reader.readUnsignedShort();
    }

    public int getStringIndex() {
        return stringIndex;
    }

    public String toString() {
        return "String\t\t\t[ #" + stringIndex + " ]";
    }
}
