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

    public int readUnsignedByte() throws IOException {
        return dataInput.readUnsignedByte();
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

    public String readUTF() throws IOException {
        return dataInput.readUTF();
    }

    public float readFloat() throws IOException {
        return dataInput.readFloat();
    }

    public long readLong() throws IOException {
        return dataInput.readLong();
    }

    public double readDouble() throws IOException {
        return dataInput.readDouble();
    }
}
