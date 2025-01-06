package xyz.natefu.model.attributes;

import xyz.natefu.ClassReader;
import xyz.natefu.util.StringUtils;

import java.io.IOException;

public record CustomAttribute(byte[] bytes,
                              String name) implements AttributeInfo {
    public CustomAttribute(ClassReader reader, String name, int length) throws IOException {
        this(reader.readNBytes(length), name);
    }

    @Override
    public String toString() {
        return name + " " + StringUtils.bufToStr(bytes);
    }
}
