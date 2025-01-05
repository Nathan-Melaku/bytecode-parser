package xyz.natefu.model.constantpool;

import xyz.natefu.model.IllegalByteCodeException;

import java.nio.ByteBuffer;

final class ConstantInteger extends ConstantPoolInfo {
    short tag = ConstantKind.CONSTANT_Integer;
    int data;

    ConstantInteger(byte[] bytes) throws IllegalArgumentException {
        if (bytes.length != 4) {
            throw new IllegalByteCodeException("Bad Integer");
        }

        data = ByteBuffer.wrap(bytes).getInt();
    }

    public String toString() {
        return "Integer\t\t\t[ " + data + " ]";
    }
}
