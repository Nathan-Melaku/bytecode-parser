package org.example.model.constantpool;

import org.example.model.ConstantKind;

import java.nio.ByteBuffer;

public final class ConstantClass extends ConstantPoolInfo {
    short tag = ConstantKind.CONSTANT_Class;
    short nameIndex;

    public ConstantClass(byte[] bytes) throws IllegalArgumentException {
        if (bytes.length != 2) {
            throw new IllegalArgumentException("2 bytes needed for a class constant");
        }

        this.nameIndex = ByteBuffer.wrap(bytes).getShort();
    }

    public short getNameIndex(){
        return nameIndex;
    }

    public String toString() {
        return "Class\t\t\t[ " +"Index #" + nameIndex + " ]";
    }
}
