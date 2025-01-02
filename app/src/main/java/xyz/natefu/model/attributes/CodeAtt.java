package xyz.natefu.model.attributes;

import xyz.natefu.util.Constants;
import xyz.natefu.model.IllegalByteCodeException;
import xyz.natefu.model.Attribute;
import xyz.natefu.model.constantpool.ConstantPool;
import xyz.natefu.util.StringUtils;

import java.nio.ByteBuffer;
import java.util.Arrays;

public record CodeAtt(short maxStack,
                      short maxLocals,
                      byte[] code,
                      ExceptionEntry[] exceptionTable,
                      Attribute[] attributes) implements AttributeInfo {

    public static CodeAtt getInstance(byte[] bytes, ConstantPool constantPool) throws IllegalByteCodeException {
        var maxStack = ByteBuffer.wrap(Arrays.copyOfRange(bytes, 0, 2)).getShort();
        var maxLocals = ByteBuffer.wrap(Arrays.copyOfRange(bytes, 2, 4)).getShort();

        // read code block
        var index = 8;
        var codeLength = ByteBuffer.wrap(Arrays.copyOfRange(bytes, 4, index)).getInt();
        var code = Arrays.copyOfRange(bytes, index, index + codeLength);
        index += codeLength;

        // read exception table
        var exceptionLength = ByteBuffer.wrap(Arrays.copyOfRange(bytes, index, index + 2)).getShort();
        index += 2;
        ExceptionEntry[] exceptions = new ExceptionEntry[exceptionLength];
        for (int i = 0; i < exceptionLength; i++) {
            exceptions[i] = new ExceptionEntry(Arrays.copyOfRange(bytes, index, index + 8));
            index += 8;
        }

        // read attributes
        var attCount = ByteBuffer.wrap(Arrays.copyOfRange(bytes, index, index + 2)).getShort();
        index += 2;

        var attributes = new Attribute[attCount];
        for (int k = 0; k < attCount; k++) {
            var attIndexBuf = Arrays.copyOfRange(bytes, index, index + 2);
            index += 2;
            var attLenBuf = Arrays.copyOfRange(bytes, index, index + 4);
            index += 4;
            if (attIndexBuf.length != 2 || attLenBuf.length != 4) {
                throw new IllegalByteCodeException(Constants.BAD_ATTRIBUTE_PARAMETER);
            }
            var attNameIndex = ByteBuffer.wrap(attIndexBuf).getShort();
            var attLength = ByteBuffer.wrap(attLenBuf).getInt();
            var read = Arrays.copyOfRange(bytes, index, index + attLength);
            index += attLength;
            if (read.length != attLength) {
                throw new IllegalByteCodeException(Constants.BAD_ATTRIBUTE_INFO_LENGTH);
            }
            attributes[k] = Attribute.getInstance(attNameIndex, read, constantPool);
        }
        return new CodeAtt(maxStack, maxLocals, code, exceptions, attributes);
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();

        sb.append("Code Block").append("\n")
                .append("\t\t\t\tmaxStack: ").append(this.maxStack).append("\n")
                .append("\t\t\t\tmaxLocals: ").append(this.maxLocals).append("\n")
                .append("\t\t\t\tcode: ").append(StringUtils.bufToStr(code)).append("\n")
                .append("\t\t\t\tExceptions:").append("\n");
        for (var e : exceptionTable) {
            sb.append(e);
        }
        for (var a : attributes) {
            sb.append(a.toString(3));
        }
        return sb.toString();
    }
}
