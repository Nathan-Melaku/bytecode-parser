package xyz.natefu.model;

import xyz.natefu.model.constantpool.ConstantPool;
import xyz.natefu.util.StringUtils;

import java.util.EnumSet;

/**
 * ClassFile
 */
public class ClassFile {
    private final byte[] magic;
    private final short minorVersion;
    private final short majorVersion;
    private final ConstantPool constantPool;
    private final EnumSet<AccessFlag> accessFlags;
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
        private EnumSet<AccessFlag> accessFlags;
        private short thisClass;
        private short superClass;
        private short[] interfaces;
        private Field[] fields;
        private Method[] methods;
        private Attribute[] attributes;

        public void magic(byte[] magic) {
            this.magic = magic;
        }

        public void minorVersion(short minorVersion) {
            this.minorVersion = minorVersion;
        }

        public void majorVersion(short majorVersion) {
            this.majorVersion = majorVersion;
        }

        public void constantPool(ConstantPool constantPool) {
            this.constantPool = constantPool;
        }

        public void accessFlags(EnumSet<AccessFlag> accessFlags) {
            this.accessFlags = accessFlags;
        }

        public void thisClass(short thisClass) {
            this.thisClass = thisClass;
        }

        public void superClass(short superClass) {
            this.superClass = superClass;
        }

        public void interfaces(short[] interfaces) {
            this.interfaces = interfaces;
        }

        public void fields(Field[] fields) {
            this.fields = fields;
        }

        public void methods(Method[] methods) {
            this.methods = methods;
        }

        public void attributes(Attribute[] attributes) {
            this.attributes = attributes;
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
