package xyz.natefu.model;

import java.util.EnumSet;

public enum AccessFlag {

    ACC_PUBLIC(0x0001),
    ACC_PRIVATE(0x0002),
    ACC_PROTECTED(0x0004),
    ACC_STATIC(0x0008),
    ACC_FINAL(0x0010),
    ACC_SUPER(0x0020),
    ACC_OPEN(0x0020),
    ACC_TRANSITIVE(0x0020),
    ACC_SYNCHRONIZED(0x0020),
    ACC_VOLATILE(0x0040),
    ACC_STATIC_PHASE(0x0040),
    ACC_BRIDGE(0x0040),
    ACC_TRANSIENT(0x0080),
    ACC_VARARGS(0x0080),
    ACC_NATIVE(0x0100),
    ACC_INTERFACE(0x0200),
    ACC_ABSTRACT(0x0400),
    ACC_STRICT(0x0800),
    ACC_SYNTHETIC(0x1000),
    ACC_ANNOTATION(0x2000),
    ACC_ENUM(0x4000),
    ACC_MODULE(0x8000),
    ACC_MANDATED(0x8000);

    private final int mask;

    AccessFlag(int mask) {
        this.mask = mask;
    }

    public static EnumSet<AccessFlag> collectAccessFlags(Context context, int accessFlag) {
        var allowedEnums = switch (context) {
            case CLASS -> EnumSet.of(ACC_PUBLIC, ACC_FINAL, ACC_SUPER, ACC_INTERFACE,
                    ACC_ABSTRACT, ACC_SYNTHETIC, ACC_ANNOTATION, ACC_ENUM, ACC_MODULE);
            case INNER_CLASS -> EnumSet.of(ACC_PUBLIC, ACC_PRIVATE, ACC_PROTECTED,
                    ACC_STATIC, ACC_FINAL, ACC_INTERFACE, ACC_ABSTRACT, ACC_SYNTHETIC, ACC_ANNOTATION, ACC_ENUM);
            case METHOD -> EnumSet.of(ACC_PUBLIC, ACC_PRIVATE, ACC_PROTECTED, ACC_STATIC, ACC_FINAL,
                    ACC_SYNCHRONIZED, ACC_BRIDGE, ACC_VARARGS, ACC_NATIVE, ACC_ABSTRACT, ACC_STRICT, ACC_SYNTHETIC);
            case METHOD_PARAMETER -> EnumSet.of(ACC_FINAL, ACC_SYNTHETIC, ACC_MANDATED);
            case FIELD -> EnumSet.of(ACC_PUBLIC, ACC_PRIVATE, ACC_PROTECTED, ACC_STATIC, ACC_FINAL, ACC_VOLATILE,
                    ACC_TRANSIENT, ACC_SYNTHETIC, ACC_ENUM);
            case MODULE -> EnumSet.of(ACC_OPEN, ACC_SYNTHETIC, ACC_MANDATED);
            case MODULE_EXPORTS, MODULE_OPENS -> EnumSet.of(ACC_SYNTHETIC, ACC_MANDATED);
            case MODULE_REQUIRES -> EnumSet.of(ACC_TRANSITIVE, ACC_STATIC_PHASE, ACC_SYNTHETIC, ACC_MANDATED);
        };

        EnumSet<AccessFlag> flags = EnumSet.noneOf(AccessFlag.class);
        for (var f : allowedEnums) {
            if ((((accessFlag & 0xFFFF) & f.mask) & 0xFFFF) == f.mask) {
                flags.add(f);
            }
        }

        return flags;
    }

    public enum Context {
        CLASS, INNER_CLASS, METHOD,
        METHOD_PARAMETER, FIELD, MODULE, MODULE_EXPORTS,
        MODULE_OPENS, MODULE_REQUIRES
    }
}