package xyz.natefu.model.constantpool;

import xyz.natefu.model.IllegalByteCodeException;

import java.nio.ByteBuffer;

public final class ConstantClass extends ConstantPoolInfo {
    short tag = ConstantKind.CONSTANT_Class;
    short nameIndex;

    public ConstantClass(byte[] bytes) throws IllegalArgumentException {
        if (bytes.length != 2) {
            throw new IllegalByteCodeException("2 bytes needed for a class constant");
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
