package xyz.natefu.model.constantpool;

import xyz.natefu.ClassReader;
import xyz.natefu.model.IllegalByteCodeException;

import java.io.IOException;

public class ConstantPool {
    public static final short CONSTANT_Utf8                = 1;
    public static final short CONSTANT_Integer             = 3;
    public static final short CONSTANT_Float               = 4;
    public static final short CONSTANT_Long                = 5;
    public static final short CONSTANT_Double              = 6;
    public static final short CONSTANT_Class               = 7;
    public static final short CONSTANT_String              = 8;
    public static final short CONSTANT_FieldRef            = 9;
    public static final short CONSTANT_MethodRef           = 10;
    public static final short CONSTANT_InterfaceMethodRef  = 11;
    public static final short CONSTANT_NameAndType         = 12;
    public static final short CONSTANT_MethodHandle        = 15;
    public static final short CONSTANT_MethodType          = 16;
    public static final short CONSTANT_Dynamic             = 17;
    public static final short CONSTANT_InvokeDynamic       = 18;
    public static final short CONSTANT_Module              = 19;
    public static final short CONSTANT_Package             = 20;

    private final ConstantPoolInfo[] constantPoolInfos;

    public ConstantPool(ConstantPoolInfo[] infos) {
        this.constantPoolInfos = infos;
    }

    public ConstantPool(ClassReader reader) throws IOException {
        // read constant pool count
        int constantPoolLen = reader.readUnsignedShort();
        ConstantPoolInfo[] infos = new ConstantPoolInfo[constantPoolLen - 1];
        // cp_info
        int i = 0;
        while (i < constantPoolLen - 1) {
            var tag = reader.readUnsignedByte();
            switch(tag) {
                case CONSTANT_Utf8 -> infos[i++] = new ConstantUtf8(reader);
                case CONSTANT_Integer ->infos[i++] = new ConstantInteger(reader);
                case CONSTANT_Float -> infos[i++]  = new ConstantFloat(reader);
                case CONSTANT_Class -> infos[i++]  = new ConstantClass(reader);
                case CONSTANT_String -> infos[i++]  = new ConstantString(reader);
                case CONSTANT_FieldRef -> infos[i++] = new ConstantFieldRef(reader);
                case CONSTANT_MethodRef -> infos[i++]  =  new ConstantMethodref(reader);
                case CONSTANT_InterfaceMethodRef -> infos[i++]  = new ConstantInterfaceMethodRef(reader);
                case CONSTANT_NameAndType ->  infos[i++]  = new ConstantNameAndType(reader);
                case CONSTANT_MethodHandle -> infos[i++]  = new ConstantMethodHandle(reader);
                case CONSTANT_MethodType -> infos[i++]  = new ConstantMethodType(reader);
                case CONSTANT_Dynamic -> infos[i++]  =  new ConstantDynamic(reader);
                case CONSTANT_InvokeDynamic -> infos[i++]  = new ConstantInvokeDynamic(reader);
                case CONSTANT_Module -> infos[i++]  = new ConstantModule(reader);
                case CONSTANT_Package -> infos[i++]  =  new ConstantPackage(reader);
                case CONSTANT_Long -> {
                    infos[i++] = new ConstantLong(reader);
                    infos[i++] = new DummyConstant();
                }
                case CONSTANT_Double -> {
                    infos[i++]  = new ConstantDouble(reader);
                    infos[i++] = new DummyConstant();
                }
                default -> throw new IllegalByteCodeException("Unknown constant " + tag);
            }
        }
        this.constantPoolInfos = infos;
    }

    public ConstantPoolInfo get(int i) throws IllegalArgumentException {
        if (i > getLength() || i < 1) throw new IllegalArgumentException();
        return constantPoolInfos[i - 1];
    }

    public int getLength(){return constantPoolInfos.length;}

    public String toString(){
        var sb = new StringBuilder();
        for (int i = 0; i < getLength(); i++) {
            if (constantPoolInfos[i] instanceof DummyConstant) {continue;}
            sb.append("\t").append("| #").append(i + 1).append(" \t")
                .append("| \t").append(constantPoolInfos[i]).append("\n");
        }
        return sb.toString();
    }
}
