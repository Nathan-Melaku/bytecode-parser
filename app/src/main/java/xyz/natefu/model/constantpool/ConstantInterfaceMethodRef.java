package xyz.natefu.model.constantpool;

import xyz.natefu.model.IllegalByteCodeException;

import java.nio.ByteBuffer;
import java.util.Arrays;

public final class ConstantInterfaceMethodRef extends ConstantPoolInfo {
    short tag = ConstantKind.CONSTANT_InterfaceMethodRef;
    short nameIndex;
    short nameAndTypeIndex;

    public ConstantInterfaceMethodRef(byte[] bytes) throws IllegalArgumentException {
        if (bytes.length != 4) {
            throw new IllegalByteCodeException("4 bytes needed for a InterfaceMethodref constant");
        }

        var nIndex = ByteBuffer.wrap(Arrays.copyOfRange(bytes, 0, 2)).getShort();
        var ntIndex = ByteBuffer.wrap(Arrays.copyOfRange(bytes, 0, 2)).getShort();
        this.nameIndex = nIndex;
        this.nameAndTypeIndex = ntIndex;
    }

    public String toString() {
        return "Interfacemethodref\t[ nameindex #" + nameIndex + ", nameAndtypeindex #" + nameAndTypeIndex + " ]";
    }
}
