package xyz.natefu.model.constantpool;

import xyz.natefu.model.ConstantKind;
import java.nio.ByteBuffer;

public final class ConstantFloat extends ConstantPoolInfo {
    short tag = ConstantKind.CONSTANT_Float;
    float data;

    public ConstantFloat(byte[] bytes) {
        if (bytes.length != 4) {
            throw new IllegalArgumentException();
        }

        data = ByteBuffer.wrap(bytes).getFloat();
    }

    public String toString() {
        return "Float\t\t\t[ " + data + " ]";
    }
}
