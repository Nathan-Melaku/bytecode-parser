package org.example.model;

public class ConstantKind {
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

    /**
     * returns number of bytes to read for each kind of constant.
     * -1 if we need the type is utf8 and 0 otherwise TODO: throw exception
     */
    public static int expectedBytes(int kind) {
        return switch(kind) {
            case ConstantKind.CONSTANT_Utf8 -> -1;
            case ConstantKind.CONSTANT_Integer, ConstantKind.CONSTANT_Float, ConstantKind.CONSTANT_FieldRef,
                 ConstantKind.CONSTANT_MethodRef, ConstantKind.CONSTANT_InterfaceMethodRef,
                 ConstantKind.CONSTANT_NameAndType, ConstantKind.CONSTANT_Dynamic,
                 ConstantKind.CONSTANT_InvokeDynamic -> 4;
            case ConstantKind.CONSTANT_Long, ConstantKind.CONSTANT_Double -> 8;
            case ConstantKind.CONSTANT_Class, ConstantKind.CONSTANT_String, ConstantKind.CONSTANT_MethodType,
                 ConstantKind.CONSTANT_Module, ConstantKind.CONSTANT_Package -> 2;
            case ConstantKind.CONSTANT_MethodHandle -> 3;
            default -> 0;
        };
    }
}
