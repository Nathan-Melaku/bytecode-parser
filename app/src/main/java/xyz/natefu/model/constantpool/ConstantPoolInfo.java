package xyz.natefu.model.constantpool;

public sealed class ConstantPoolInfo permits ConstantUtf8, ConstantInteger, ConstantFloat, ConstantLong,
ConstantDouble,ConstantClass,ConstantString, ConstantFieldRef, ConstantMethodref, ConstantInterfaceMethodRef,
ConstantNameAndType, ConstantMethodHandle, ConstantMethodType, ConstantDynamic, ConstantInvokeDynamic,
ConstantModule, ConstantPackage {
    public short tag;
}

