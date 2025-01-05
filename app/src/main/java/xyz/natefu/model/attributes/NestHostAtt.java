package xyz.natefu.model.attributes;

import xyz.natefu.ClassReader;

import java.io.IOException;

public record NestHostAtt (int hostClassIndex) implements AttributeInfo{

    public static NestHostAtt getInstance(ClassReader reader) throws IOException {
        reader.skipBytes(4);
        int hostClassIndex = reader.readUnsignedShort();
        return new NestHostAtt(hostClassIndex);
    }
}
