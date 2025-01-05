package xyz.natefu.model.attributes;

import xyz.natefu.ClassReader;
import xyz.natefu.util.StringUtils;

import java.io.IOException;

public class DefaultAtt implements AttributeInfo {
    final byte[] bytes;

    public DefaultAtt(ClassReader reader) throws IOException {
        var length = reader.readInt();
        this.bytes = reader.readNBytes(length);
    }

    @Override
    public String toString() {
        return StringUtils.bufToStr(bytes);
    }
}
