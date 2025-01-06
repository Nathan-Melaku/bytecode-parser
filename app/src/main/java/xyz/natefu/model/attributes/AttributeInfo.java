package xyz.natefu.model.attributes;

public sealed interface AttributeInfo permits BootStrapMethodsAttribute, CodeAttribute,ConstantValueAttribute,
        CustomAttribute, LineNumberTableAttribute, NestHostAttribute, SourceFileAttribute, StackMapTableAttribute,
        NestMembersAttribute, InnerClassesAttribute { }
