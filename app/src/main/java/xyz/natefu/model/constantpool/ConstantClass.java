package xyz.natefu.model.constantpool;

import xyz.natefu.ClassReader;

import java.io.IOException;

public final class ConstantClass extends ConstantPoolInfo {
    short tag = ConstantPool.CONSTANT_Class;
    int nameIndex;

    public ConstantClass(ClassReader reader) throws IOException {
        this.nameIndex = reader.readUnsignedShort();
    }

    public int getNameIndex(){
        return nameIndex;
    }

    public String toString() {
        return "Class\t\t\t[ " +"Index #" + nameIndex + " ]";
    }
}
