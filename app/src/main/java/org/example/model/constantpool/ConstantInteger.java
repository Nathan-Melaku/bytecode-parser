package org.example.model.constantpool;

import org.example.model.ConstantKind;
import java.nio.ByteBuffer;

final class ConstantInteger extends ConstantPoolInfo {
    short tag = ConstantKind.CONSTANT_Integer;
    int data;

    ConstantInteger(byte[] bytes) throws IllegalArgumentException {
        if (bytes.length != 4) {
            System.out.println("len: " + bytes.length);
            throw new IllegalArgumentException();
        }

        data = ByteBuffer.wrap(bytes).getInt();
    }

    public String toString() {
        return "Integer\t\t\t[ " + data + " ]";
    }
}
