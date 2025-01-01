package xyz.natefu.model.constantpool;

import xyz.natefu.model.ConstantKind;
import java.nio.ByteBuffer;

public final class ConstantDouble extends ConstantPoolInfo {

    short tag = ConstantKind.CONSTANT_Double;
    double data;

    public ConstantDouble(byte[] bytes) {
        data = ByteBuffer.wrap(bytes).getDouble();
    }

    public String toString() {
        return "Double\t\t\t[ " + data + " ]";
    }
}