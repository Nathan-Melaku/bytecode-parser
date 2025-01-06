package xyz.natefu.model.attributes;

import xyz.natefu.ClassReader;

import java.io.IOException;

public record ConstantValueAttribute(int valueIndex) implements AttributeInfo{
    public static ConstantValueAttribute getInstance(ClassReader reader) throws IOException {
        var index = reader.readUnsignedShort();
        return new ConstantValueAttribute(index);
    }
}
