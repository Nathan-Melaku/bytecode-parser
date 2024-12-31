package org.example.model.constantpool;

import org.example.model.ConstantKind;
import java.nio.ByteBuffer;

public final class ConstantLong extends ConstantPoolInfo {

    short tag = ConstantKind.CONSTANT_Long;
    long data;

    public ConstantLong(byte[] bytes) {
        data = ByteBuffer.wrap(bytes).getLong();
    }

    public String toString() {
        return "Long\t\t\t[ " + data + " ]";
    }
}
