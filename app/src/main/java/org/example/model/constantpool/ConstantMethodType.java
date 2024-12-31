package org.example.model.constantpool;

import org.example.model.ConstantKind;
import java.nio.ByteBuffer;

public final class ConstantMethodType extends ConstantPoolInfo {

    short tag = ConstantKind.CONSTANT_MethodType;
    short descriptorIndex;

    public ConstantMethodType(byte[] bytes) throws IllegalArgumentException {
        if (bytes.length != 2) {
            throw new IllegalArgumentException();
        }

        descriptorIndex = ByteBuffer.wrap(bytes).getShort();
    }

    public String toString() {
        return "MethodType \t[ " + "descriptorindex #" + descriptorIndex + " ]";
    }
}
