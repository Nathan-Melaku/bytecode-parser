package xyz.natefu.model.attributes;

import xyz.natefu.util.StringUtils;

public class DefaultAtt implements AttributeInfo {
    final byte[] bytes;

    public DefaultAtt(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public String toString() {
        return StringUtils.bufToStr(bytes);
    }
}
