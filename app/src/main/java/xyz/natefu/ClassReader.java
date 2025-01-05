package xyz.natefu;

import xyz.natefu.model.constantpool.ConstantPool;
import xyz.natefu.model.constantpool.ConstantPoolInfo;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ClassReader {
    private final DataInputStream dataInput;
    private ConstantPool constantPool;

    public ClassReader(InputStream in){
        this.dataInput = new DataInputStream(new BufferedInputStream(in));
    }

    public int readUnsignedShort() throws IOException {
        return dataInput.readUnsignedShort();
    }

    public byte readByte() throws IOException {
        return dataInput.readByte();
    }

    public byte[] readNBytes(int bytesToRead) throws IOException {
        return dataInput.readNBytes(bytesToRead);
    }

    public int readInt() throws IOException {
        return dataInput.readInt();
    }

    public void skipBytes(int i) throws IOException {
        dataInput.skipBytes(i);
    }

    public ConstantPoolInfo getConstant(int index) {
        return constantPool.get(index);
    }

    public void setConstantPool(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }
}
