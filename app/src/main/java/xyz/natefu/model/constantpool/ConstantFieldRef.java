package xyz.natefu.model.constantpool;

import xyz.natefu.ClassReader;

import java.io.IOException;

public final class ConstantFieldRef extends ConstantPoolInfo {
    short tag = ConstantPool.CONSTANT_FieldRef;
    int classIndex;
    int nameAndTypeIndex;

    public ConstantFieldRef(ClassReader reader) throws IOException {
        this.classIndex = reader.readUnsignedShort();
        this.nameAndTypeIndex = reader.readUnsignedShort();
    }

    public String toString() {
        return "Fieldref\t\t[ classIndex #" + classIndex + ", nameAndTypeIndex #" + nameAndTypeIndex + " ]";
    }
}
