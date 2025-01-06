package xyz.natefu.model;

import xyz.natefu.ClassReader;
import xyz.natefu.util.StringUtils;

import java.io.IOException;
import java.util.EnumSet;

public record Field(EnumSet<AccessFlag> accessFlags,
                    int nameIndex,
                    int descriptorIndex,
                    Attribute[] attributes) {

    public static Field getInstance(ClassReader reader, Attribute.Factory attributeFactory) throws IOException {
        var accessFlag = reader.readUnsignedShort();
        var accessFlags = AccessFlag.collectAccessFlags(AccessFlag.Context.FIELD, accessFlag);
        var nameIndex = reader.readUnsignedShort();
        var descriptorIndex = reader.readUnsignedShort();
        var attributes = attributeFactory.readAttributes();
        return new Field(
            accessFlags,
            nameIndex,
            descriptorIndex,
            attributes
        );
    }

    @Override
    public String toString() {
        return "Field:" + "\n" +
               "\taccessFlags: " + this.accessFlags + "\n" +
               "\tnameIndex #" + this.nameIndex + " " + "\n" +
               "\tdescriptorIndex #" + this.descriptorIndex + "\n" +
               StringUtils.attrsToStr(this.attributes) + "\n";
    }
}
