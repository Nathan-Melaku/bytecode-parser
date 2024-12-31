package org.example.model;

import org.example.model.constantpool.ConstantPool;
import org.example.util.StringUtils;

public class Method {
    private final byte[] accessFlags;
    private final short nameIndex;
    private final short descriptorIndex;
    private final Attribute[] attributes;

    public Method(byte[] accessFlags, short nameIndex, short descriptorIndex, Attribute[] attributes) {
        this.accessFlags = accessFlags;
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
        this.attributes = attributes;
    }

    public String toString(ConstantPool constantPool) {
        var sb = new StringBuilder();
        sb.append("\tMethod:").append("\n");
        sb.append("\t\taccessFlags ").append(StringUtils.bufToStr(accessFlags)).append("\n");
        sb.append("\t\tnameIndex #").append(nameIndex).append(" ").append(StringUtils.getUtf8(nameIndex, constantPool)).append("\n");
        sb.append("\t\tdescriptorIndex #").append(descriptorIndex).append(" ")
            .append(StringUtils.getUtf8(descriptorIndex, constantPool)).append("\n");
        sb.append(StringUtils.attsToStr(attributes, constantPool)).append("\n");
        return sb.toString();
    }


}
