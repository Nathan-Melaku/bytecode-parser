package xyz.natefu.model.attributes;

import xyz.natefu.ClassReader;
import xyz.natefu.model.Attribute;
import xyz.natefu.model.IllegalByteCodeException;
import xyz.natefu.util.StringUtils;

import java.io.IOException;

public record CodeAtt(int maxStack,
                      int maxLocals,
                      byte[] code,
                      ExceptionEntry[] exceptionTable,
                      Attribute[] attributes) implements AttributeInfo {

    public static CodeAtt getInstance(ClassReader reader, Attribute.Factory factory) throws IllegalByteCodeException, IOException {
        reader.skipBytes(4);
        var maxStack = reader.readUnsignedShort();
        var maxLocals = reader.readUnsignedShort();

        // read code block
        var codeLength = reader.readInt();
        var code = reader.readNBytes(codeLength);

        // read exception table
        var exceptionLength = reader.readUnsignedShort();
        ExceptionEntry[] exceptions = new ExceptionEntry[exceptionLength];
        for (int i = 0; i < exceptionLength; i++) {
            exceptions[i] = new ExceptionEntry(reader.readNBytes(8));
        }

        // read attributes
        var attributes = factory.readAttributes();
        return new CodeAtt(maxStack, maxLocals, code, exceptions, attributes);
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();

        sb.append("Code Block").append("\n")
                .append("\tmaxStack: ").append(this.maxStack).append("\n")
                .append("\tmaxLocals: ").append(this.maxLocals).append("\n")
                .append("\tcode: ").append(StringUtils.bufToStr(code)).append("\n")
                .append("\tExceptions:").append("\n");
        for (var e : exceptionTable) {
            sb.append(e);
        }
        for (var a : attributes) {
            sb.append(a);
        }
        return sb.toString();
    }
}
