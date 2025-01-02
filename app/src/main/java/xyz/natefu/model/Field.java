package xyz.natefu.model;

import xyz.natefu.model.constantpool.ConstantPool;
import xyz.natefu.util.StringUtils;

import java.util.EnumSet;

/**
 * @param constantPool constantPool here only for toString. not sure if it is still worth it
 */
public record Field(ConstantPool constantPool,
                    EnumSet<AccessFlag> accessFlags,
                    short nameIndex,
                    short descriptorIndex,
                    Attribute[] attributes) {

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
