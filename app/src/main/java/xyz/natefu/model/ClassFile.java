package xyz.natefu.model;

import xyz.natefu.ClassReader;
import xyz.natefu.model.constantpool.ConstantPool;
import xyz.natefu.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.EnumSet;
import java.util.Objects;

/**
 * ClassFile
 */
public record ClassFile(int magic, int minorVersion, int majorVersion, ConstantPool constantPool,
                        EnumSet<AccessFlag> accessFlags, int thisClass, int superClass, int[] interfaces,
                        Field[] fields, Method[] methods, Attribute[] attributes) {

    public ClassFile {
        Objects.requireNonNull(constantPool);
        Objects.requireNonNull(accessFlags);
        Objects.requireNonNull(interfaces);
        Objects.requireNonNull(fields);
        Objects.requireNonNull(methods);
        Objects.requireNonNull(attributes);
    }

    public static ClassFile fromInputStream(InputStream stream) throws IllegalByteCodeException, IOException {
        var reader = new ClassReader(stream);
        var magic = reader.readInt();
        var minorVersion = reader.readUnsignedShort();
        var majorVersion = reader.readUnsignedShort();
        var constantPool = ConstantPool.getConstantPool(reader);
        reader.setConstantPool(constantPool);
        var attributeFactory = new Attribute.Factory(reader);
        var accFlags = reader.readUnsignedShort();
        var accessFlagsEnumSet = AccessFlag.collectAccessFlags(AccessFlag.Context.CLASS, accFlags);
        var thisClass = reader.readUnsignedShort();
        var superClass = reader.readUnsignedShort();
        var interfaceCount = reader.readUnsignedShort();
        int[] interfaces = new int[interfaceCount];
        for (int j = 0; j < interfaceCount; j++) {
            interfaces[j] = reader.readUnsignedShort();
        }
        var field_count = reader.readUnsignedShort();
        Field[] fields = new Field[field_count];
        for (int j = 0; j < field_count; j++) {
            var accessFlag = reader.readUnsignedShort();
            var accessFlags = AccessFlag.collectAccessFlags(AccessFlag.Context.FIELD, accessFlag);
            var nameIndex = reader.readUnsignedShort();
            var descriptorIndex = reader.readUnsignedShort();
            var attributes = attributeFactory.readAttributes();
            fields[j] = new Field(
                    accessFlags,
                    nameIndex,
                    descriptorIndex,
                    attributes);
        }

        // read methods
        var methodCount = reader.readUnsignedShort();
        Method[] methods = new Method[methodCount];
        for (int j = 0; j < methodCount; j++) {
            var accFlag = reader.readUnsignedShort();
            var accessFlags = AccessFlag.collectAccessFlags(AccessFlag.Context.METHOD, accFlag);
            var nameIndex = reader.readUnsignedShort();
            var descriptorIndex = reader.readUnsignedShort();
            var attributes = attributeFactory.readAttributes();
            methods[j] = new Method(
                    accessFlags,
                    nameIndex,
                    descriptorIndex,
                    attributes);
        }

        var attributes = attributeFactory.readAttributes();

        return new ClassFile(magic,minorVersion,majorVersion,
                constantPool,accessFlagsEnumSet,thisClass,
                superClass,interfaces,fields,methods,attributes);
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        sb.append("Magic: ").append(magic).append("\n");
        sb.append("MinorVersion: ").append(this.minorVersion).append("\n");
        sb.append("MajorVersion: ").append(this.majorVersion).append("\n");
        sb.append("ConstantPool: ").append("\n").append(this.constantPool).append("\n");
        sb.append("AccessFlags: ").append(accessFlags).append("\n");
        sb.append("ThisClass: #").append(this.thisClass).append(" ")
                .append(StringUtils.getClassName(this.thisClass, this.constantPool)).append("\n");
        sb.append("SuperClass: #").append(this.superClass).append(" ")
                .append(StringUtils.getClassName(this.superClass, this.constantPool)).append("\n");
        sb.append("Interfaces: ");
        for (var i : this.interfaces) {
            sb.append("[ ").append("#").append(i).append(" ")
                    .append(StringUtils.getClassName(i, this.constantPool)).append(" ] ");
        }
        sb.append("\n");
        sb.append("Fields: ").append(fields.length).append("\n");
        for (var f : fields) {
            sb.append(f);
        }
        sb.append("Methods: ").append(methods.length).append("\n");
        for (var m : methods) {
            sb.append(m);
        }

        sb.append("Attributes: ").append(attributes.length).append("\n");
        for (var a : attributes) {
            sb.append(a);
        }

        return sb.toString();
    }
}
