package xyz.natefu.model.attributes;

import xyz.natefu.ClassReader;

import java.io.IOException;
import java.util.Arrays;

public record LineNumberTableAttr (LineNumber[] lineNumberTable) implements AttributeInfo {

    public static LineNumberTableAttr getInstance(ClassReader reader) throws IOException {

        reader.skipBytes(4);
        var lineNumberTableLength = reader.readUnsignedShort();
        LineNumber[] lineNumbers = new LineNumber[lineNumberTableLength];
        for (int i = 0; i < lineNumberTableLength; i++) {
            var sPc = reader.readUnsignedShort();
            var lineNumber = reader.readUnsignedShort();
            lineNumbers[i] = new LineNumber(sPc, lineNumber);
        }

        return new LineNumberTableAttr(lineNumbers);
    }

    public record LineNumber(int startPc, int lineNumber) {}

    @Override
    public String toString() {
        return "LineNumberTable=[" + Arrays.toString(lineNumberTable) + "]";
    }
}
