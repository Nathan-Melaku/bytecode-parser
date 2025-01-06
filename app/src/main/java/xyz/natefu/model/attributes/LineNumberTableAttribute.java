package xyz.natefu.model.attributes;

import xyz.natefu.ClassReader;

import java.io.IOException;
import java.util.Arrays;

public record LineNumberTableAttribute(LineNumber[] lineNumberTable) implements AttributeInfo {

    public static LineNumberTableAttribute getInstance(ClassReader reader) throws IOException {
        var lineNumberTableLength = reader.readUnsignedShort();
        LineNumber[] lineNumbers = new LineNumber[lineNumberTableLength];
        for (int i = 0; i < lineNumberTableLength; i++) {
            var sPc = reader.readUnsignedShort();
            var lineNumber = reader.readUnsignedShort();
            lineNumbers[i] = new LineNumber(sPc, lineNumber);
        }

        return new LineNumberTableAttribute(lineNumbers);
    }

    public record LineNumber(int startPc, int lineNumber) {}

    @Override
    public String toString() {
        return "LineNumberTable=[" + Arrays.toString(lineNumberTable) + "]";
    }
}
