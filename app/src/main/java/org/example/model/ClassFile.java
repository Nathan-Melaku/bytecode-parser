package org.example.model;

import org.example.model.constantpool.ConstantPool;
import org.example.util.StringUtils;

/**
 * ClassFile
 */
public class ClassFile {
    private final byte[] magic;
    private final short minorVersion;
    private final short majorVersion;
    private final ConstantPool constantPool;
    private final byte[] accessFlags;
    private final short thisClass;
    private final short superClass;
    private final short[] interfaces;
    private final Field[] fields;
    private final Method[] methods;
    private final Attribute[] attributes;

    protected ClassFile(Builder builder) {
        this.magic = builder.magic;
        this.minorVersion = builder.minorVersion;
        this.majorVersion = builder.majorVersion;
        this.constantPool = builder.constantPool;
        this.accessFlags = builder.accessFlags;
        this.thisClass = builder.thisClass;
        this.superClass = builder.superClass;
        this.interfaces = builder.interfaces;
        this.fields = builder.fields;
        this.methods = builder.methods;
        this.attributes = builder.attributes;
    }

    public static class Builder {
        private byte[] magic;
        private short minorVersion;
        private short majorVersion;
        private ConstantPool constantPool;
        private byte[] accessFlags;
        private short thisClass;
        private short superClass;
        private short[] interfaces;
        private Field[] fields;
        private Method[] methods;
        private Attribute[] attributes;

        public Builder magic(byte[] magic) {
            this.magic = magic;
            return this;
        }

        public Builder minorVersion(short minorVersion) {
            this.minorVersion = minorVersion;
            return this;
        }

        public Builder majorVersion(short majorVersion) {
            this.majorVersion = majorVersion;
            return this;
        }

        public Builder constantPool(ConstantPool constantPool) {
            this.constantPool = constantPool;
            return this;
        }

        public Builder accessFlags(byte[] accessFlags) {
            this.accessFlags = accessFlags;
            return this;
        }

        public Builder thisClass(short thisClass) {
            this.thisClass = thisClass;
            return this;
        }

        public Builder superClass(short superClass) {
            this.superClass = superClass;
            return this;
        }

        public Builder interfaces(short[] interfaces) {
            this.interfaces = interfaces;
            return this;
        }

        public Builder fields(Field[] fields) {
            this.fields = fields;
            return this;
        }

        public Builder methods(Method[] methods) {
            this.methods = methods;
            return this;
        }

        public Builder attributes(Attribute[] attributes) {
            this.attributes = attributes;
            return this;
        }

        public ClassFile build() {
            return new ClassFile(this);
        }
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        sb.append("Magic: ").append(StringUtils.bufToStr(this.magic)).append("\n");
        sb.append("MinorVersion: ").append(this.minorVersion).append("\n");
        sb.append("MajorVersion: ").append(this.majorVersion).append("\n");
        sb.append("ConstantPool: ").append("\n").append(this.constantPool).append("\n");
        sb.append("AccessFlags: ").append(StringUtils.bufToStr(accessFlags)).append("\n");
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
            sb.append(f.toString(this.constantPool));
        }
        sb.append("Methods: ").append(methods.length).append("\n");
        for (var m : methods) {
            sb.append(m.toString(this.constantPool));
        }

        sb.append("Attributes: ").append(attributes.length).append("\n");
        for (var a : attributes) {
            sb.append(a.toString((short) 2, this.constantPool));
        }

        return sb.toString();
    }
}
