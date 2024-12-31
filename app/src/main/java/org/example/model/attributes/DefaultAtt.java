package org.example.model.attributes;

import org.example.util.StringUtils;

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
