package xyz.natefu.model.attributes;

import xyz.natefu.ClassReader;

import java.io.IOException;

public record SourceFileAttribute(int sourceFileIndex) implements AttributeInfo {
    public static SourceFileAttribute getInstance(ClassReader reader) throws IOException {
        return new SourceFileAttribute(reader.readUnsignedShort());
    }
}
