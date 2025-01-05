package xyz.natefu.model;

import xyz.natefu.util.StringUtils;

import java.util.EnumSet;

public record Method(EnumSet<AccessFlag> accessFlags,
                     int nameIndex,
                     int descriptorIndex,
                     Attribute[] attributes) {

    @Override
    public String toString() {
        return "Method:" + "\n" +
               "\taccessFlags " + this.accessFlags + "\n" +
               "\tnameIndex #" + this.nameIndex + "\n" +
               "\tdescriptorIndex #" + this.descriptorIndex + "\n" +
               StringUtils.attrsToStr(this.attributes) + "\n";
    }
}
