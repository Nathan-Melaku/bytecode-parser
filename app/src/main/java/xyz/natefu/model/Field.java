package xyz.natefu.model;

import xyz.natefu.model.constantpool.ConstantPool;
import xyz.natefu.util.StringUtils;

import java.util.EnumSet;

public class Field {
    /* constantPool here only for toString. not sure if it is still worth it*/
    private final ConstantPool constantPool;
    private final EnumSet<AccessFlag> accessFlags;
    private final short nameIndex;
    private final short descriptorIndex;
    private final Attribute[] attributes;

    public Field( ConstantPool constantPool, EnumSet<AccessFlag> accessFlags,
                  short nameIndex, short descriptorIndex, Attribute[] attributes) {
        this.constantPool = constantPool;
        this.accessFlags = accessFlags;
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        sb.append("\tField:").append("\n");
        sb.append("\t\taccessFlags: ").append(this.accessFlags).append("\n");
        sb.append("\t\tnameIndex #").append(this.nameIndex).append(" ").append(StringUtils.getUtf8(this.nameIndex, this.constantPool)).append("\n");
        sb.append("\t\tdescriptorIndex #").append(this.descriptorIndex).append(" ")
            .append(StringUtils.getUtf8(this.descriptorIndex, this.constantPool)).append("\n");
        sb.append(StringUtils.attrsToStr(this.attributes)).append("\n");
        return sb.toString();
    }
}
