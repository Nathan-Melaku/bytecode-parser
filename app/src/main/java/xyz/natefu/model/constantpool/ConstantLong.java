package xyz.natefu.model.constantpool;

import xyz.natefu.model.IllegalByteCodeException;

import java.nio.ByteBuffer;

public final class ConstantLong extends ConstantPoolInfo {

    short tag = ConstantKind.CONSTANT_Long;
    long data;

    public ConstantLong(byte[] bytes) {
        if (bytes.length != 4 ) {
            throw new IllegalByteCodeException("Bad Long constant");
        }
        data = ByteBuffer.wrap(bytes).getLong();
    }

    public String toString() {
        return "Long\t\t\t[ " + data + " ]";
    }
}
