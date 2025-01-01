package xyz.natefu.model.constantpool;

import xyz.natefu.model.ConstantKind;
import java.nio.ByteBuffer;

public final class ConstantModule extends ConstantPoolInfo {
    short tag = ConstantKind.CONSTANT_Module;
    short nameIndex;

    public ConstantModule(byte[] bytes){
        if (bytes.length != 2) {
            throw new IllegalArgumentException();
        }

        nameIndex = ByteBuffer.wrap(bytes).getShort();
    }

    public String toString() {
        return "Module [ nameIndex #" + nameIndex + " ]";
    }
}
