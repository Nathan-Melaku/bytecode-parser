package xyz.natefu.model.attributes;

import xyz.natefu.ClassReader;

import java.io.IOException;

public record ConstantValueAtt (int valueIndex) implements AttributeInfo{
    public static ConstantValueAtt getInstance(ClassReader reader) throws IOException {
        reader.skipBytes(4);
        var index = reader.readUnsignedShort();
        return new ConstantValueAtt(index);
    }
}
