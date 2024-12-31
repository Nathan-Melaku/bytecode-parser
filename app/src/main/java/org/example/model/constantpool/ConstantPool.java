package org.example.model.constantpool;

import org.example.model.ConstantKind;

public class ConstantPool {
    private final ConstantPoolInfo[] constantPool;
    private short index = 0;

    public ConstantPool(short length) {
        constantPool = new ConstantPoolInfo[length];
    }

    public void add(short tag, byte[] bytes){
        switch(tag) {
            case ConstantKind.CONSTANT_Utf8:
                constantPool[index] = new ConstantUtf8(bytes);
                index++;
                break;
            case ConstantKind.CONSTANT_Integer:
                constantPool[index] = new ConstantInteger(bytes);
                index++;
                break;
            case ConstantKind.CONSTANT_Float:
                constantPool[index] = new ConstantFloat(bytes);
                index++;
                break;
            case ConstantKind.CONSTANT_Long:
                constantPool[index] = new ConstantLong(bytes);
                index++;
                break;
            case ConstantKind.CONSTANT_Double:
                constantPool[index] = new ConstantDouble(bytes);
                index++;
                break;
            case ConstantKind.CONSTANT_Class:
                constantPool[index] = new ConstantClass(bytes);
                index++;
                break;
            case ConstantKind.CONSTANT_String:
                constantPool[index] = new ConstantString(bytes);
                index++;
                break;
            case ConstantKind.CONSTANT_FieldRef:
                constantPool[index] = new ConstantFieldRef(bytes);
                index++;
                break;
            case ConstantKind.CONSTANT_MethodRef:
                constantPool[index] = new ConstantMethodref(bytes);
                index++;
                break;
            case ConstantKind.CONSTANT_InterfaceMethodRef:
                constantPool[index] = new ConstantInterfaceMethodRef(bytes);
                index++;
                break;
            case ConstantKind.CONSTANT_NameAndType:
                constantPool[index] = new ConstantNameAndType(bytes);
                index++;
                break;
            case ConstantKind.CONSTANT_MethodHandle:
                constantPool[index] = new ConstantMethodHandle(bytes);
                index++;
                break;
            case ConstantKind.CONSTANT_MethodType:
                constantPool[index] = new ConstantMethodType(bytes);
                index++;
                break;
            case ConstantKind.CONSTANT_Dynamic:
                constantPool[index] = new ConstantDynamic(bytes);
                index++;
                break;
            case ConstantKind.CONSTANT_InvokeDynamic:
                constantPool[index] = new ConstantInvokeDynamic(bytes);
                index++;
                break;
            case ConstantKind.CONSTANT_Module:
                constantPool[index] = new ConstantModule(bytes);
                index++;
                break;
            case ConstantKind.CONSTANT_Package:
                constantPool[index] = new ConstantPackage(bytes);
                index++;
                break;
        }
    }

    public ConstantPoolInfo get(int i) throws IllegalArgumentException {
        if (i > index || i < 1) throw new IllegalArgumentException();
        return constantPool[i - 1];
    }

    public short getLength(){return index;}

    public String toString(){
        var sb = new StringBuilder();
        for (int i = 0; i < index; i++) {
            sb.append("\t").append("| #").append(i + 1).append(" \t")
                .append("| \t").append(constantPool[i].toString())
                .append("\n");
        }
        return sb.toString();
    }
}
