package xyz.natefu.model.attributes;

import xyz.natefu.ClassReader;
import xyz.natefu.model.Attribute;
import xyz.natefu.model.IllegalByteCodeException;
import xyz.natefu.util.StringUtils;

import java.io.IOException;

public record CodeAttribute(int maxStack,
                            int maxLocals,
                            byte[] code,
                            ExceptionEntry[] exceptionTable,
                            Attribute[] attributes) implements AttributeInfo {

    public static CodeAttribute getInstance(ClassReader reader, Attribute.Factory factory) throws IllegalByteCodeException, IOException {
        var maxStack = reader.readUnsignedShort();
        var maxLocals = reader.readUnsignedShort();

        // read code block
        var codeLength = reader.readInt();
        var code = reader.readNBytes(codeLength);

        // read exception table
        var exceptionLength = reader.readUnsignedShort();
        ExceptionEntry[] exceptions = new ExceptionEntry[exceptionLength];
        for (int i = 0; i < exceptionLength; i++) {
            int startPc = reader.readUnsignedShort();
            int endPc = reader.readUnsignedShort();
            int handlerPc = reader.readUnsignedShort();
            int catchPc = reader.readUnsignedShort();
            exceptions[i] = new ExceptionEntry(startPc, endPc, handlerPc, catchPc);
        }

        // read attributes
        var attributes = factory.readAttributes();
        return new CodeAttribute(maxStack, maxLocals, code, exceptions, attributes);
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

    public record ExceptionEntry(int startPc,
                                 int endPc,
                                 int handlerPc,
                                 int catchPc) {
        @Override
        public String toString() {
            var sb = new StringBuilder();
            sb.append("\t\t\tstartPc: ").append(this.startPc).append("\n")
                    .append("\t\t\tendPc: ").append(this.endPc).append("\n")
                    .append("\t\t\thandlerPc: ").append(this.handlerPc).append("\n")
                    .append("\t\t\tcatchPc: ").append(this.catchPc).append("\n");
            return sb.toString();
        }
    }
}
