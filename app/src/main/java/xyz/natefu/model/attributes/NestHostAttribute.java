package xyz.natefu.model.attributes;

import xyz.natefu.ClassReader;

import java.io.IOException;

public record NestHostAttribute(int hostClassIndex) implements AttributeInfo{

    public static NestHostAttribute getInstance(ClassReader reader) throws IOException {
        int hostClassIndex = reader.readUnsignedShort();
        return new NestHostAttribute(hostClassIndex);
    }
}
