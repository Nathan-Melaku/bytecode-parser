package xyz.natefu.model.constantpool;

import xyz.natefu.ClassReader;
import xyz.natefu.model.IllegalByteCodeException;
import xyz.natefu.util.Constants;
import xyz.natefu.util.StringUtils;

import java.io.IOException;

public class ConstantPool {
    private final ConstantPoolInfo[] constantPool;
    private short index = 0;

    public ConstantPool(int length) {
        constantPool = new ConstantPoolInfo[length];
    }

    public static ConstantPool getConstantPool(ClassReader reader) throws IOException {
        // read constant pool count
        int constantPoolLen = reader.readUnsignedShort();
        ConstantPool cp = new ConstantPool(constantPoolLen);
        // cp_info
        for (int j = 0; j < constantPoolLen - 1; j++) {
            var tag = (short) reader.readByte();
            var bytesToRead = ConstantKind.expectedBytes(tag);
            if (bytesToRead == -1) {
                bytesToRead = reader.readUnsignedShort();
            }
            var bytesRead = reader.readNBytes(bytesToRead);
            if (bytesRead.length != bytesToRead) {
                throw new IllegalByteCodeException(Constants.BAD_CONSTANT_POOL_VALUE);
            }
            var constant = cp.add(tag, bytesRead);
            // if we add a Double or a long constant the next entry have to be valid but not usable (aka dummy)
            if ( constant instanceof ConstantDouble || constant instanceof ConstantLong){
                cp.add(ConstantKind.CONSTANT_Dummy, null);
                j++;
            }
        }
        return cp;
    }


    public ConstantPoolInfo add(short tag, byte[] bytes) {
        return switch(tag) {
            case ConstantKind.CONSTANT_Utf8 -> {
                var temp = new ConstantUtf8(bytes);
                constantPool[index++] = temp;
                yield temp;
            }
            case ConstantKind.CONSTANT_Integer -> {
                var temp = new ConstantInteger(bytes);
                constantPool[index++] = temp;
                yield temp;
            }
            case ConstantKind.CONSTANT_Float -> {
                var temp = new ConstantFloat(bytes);
                constantPool[index++] = temp;
                yield temp;
            }
            case ConstantKind.CONSTANT_Long -> {
                var temp = new ConstantLong(bytes);
                constantPool[index++] = temp;
                yield temp;
            }
            case ConstantKind.CONSTANT_Double -> {
                var temp = new ConstantDouble(bytes);
                constantPool[index++] = temp;
                yield temp;
            }
            case ConstantKind.CONSTANT_Class -> {
                var temp = new ConstantClass(bytes);
                constantPool[index++] = temp;
                yield temp;
            }
            case ConstantKind.CONSTANT_String -> {
                var temp = new ConstantString(bytes);
                constantPool[index++] = temp;
                yield temp;
            }
            case ConstantKind.CONSTANT_FieldRef -> {
                var temp = new ConstantFieldRef(bytes);
                constantPool[index++] = temp;
                yield temp;
            }
            case ConstantKind.CONSTANT_MethodRef -> {
                var temp =  new ConstantMethodref(bytes);
                constantPool[index++] = temp;
                yield temp;
            }
            case ConstantKind.CONSTANT_InterfaceMethodRef -> {
                var temp = new ConstantInterfaceMethodRef(bytes);
                constantPool[index++] = temp;
                yield temp;
            }
            case ConstantKind.CONSTANT_NameAndType -> {
                var temp = new ConstantNameAndType(bytes);
                constantPool[index++] = temp;
                yield temp;
            }
            case ConstantKind.CONSTANT_MethodHandle -> {
                var temp = new ConstantMethodHandle(bytes);
                constantPool[index++] = temp;
                yield temp;
            }
            case ConstantKind.CONSTANT_MethodType -> {
                var temp = new ConstantMethodType(bytes);
                constantPool[index++] = temp;
                yield temp;
            }
            case ConstantKind.CONSTANT_Dynamic -> {
                var temp =  new ConstantDynamic(bytes);
                constantPool[index] = temp;
                yield temp;
            }
            case ConstantKind.CONSTANT_InvokeDynamic -> {
                var temp = new ConstantInvokeDynamic(bytes);
                constantPool[index++] = temp;
                yield temp;
            }
            case ConstantKind.CONSTANT_Module -> {
                var temp = new ConstantModule(bytes);
                constantPool[index++] = temp;
                yield temp;
            }
            case ConstantKind.CONSTANT_Package -> {
                var temp =  new ConstantPackage(bytes);
                constantPool[index++] = temp;
                yield temp;
            }
            case ConstantKind.CONSTANT_Dummy -> {
                var temp = new DummyConstant();
                constantPool[index++] = temp;
                yield temp;
            }
            default -> throw new IllegalByteCodeException("Unknown constant " + StringUtils.bufToStr(bytes));
        };
    }

    public ConstantPoolInfo get(int i) throws IllegalArgumentException {
        if (i > index || i < 1) throw new IllegalArgumentException();
        return constantPool[i - 1];
    }

    public short getLength(){return index;}

    public String toString(){
        var sb = new StringBuilder();
        for (int i = 0; i < index; i++) {
            if (constantPool[i] instanceof DummyConstant) {continue;}
            sb.append("\t").append("| #").append(i + 1).append(" \t")
                .append("| \t").append(constantPool[i]).append("\n");
        }
        return sb.toString();
    }
}
