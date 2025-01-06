package xyz.natefu.model.attributes;

import xyz.natefu.ClassReader;

import java.io.IOException;
import java.util.Arrays;

public record NestMembersAttribute(int[] classes) implements AttributeInfo {
    public static NestMembersAttribute getInstance(ClassReader reader) throws IOException {
        var numberOfClasses = reader.readUnsignedShort();
        int[] cls = new int[numberOfClasses];
        for (int i = 0; i < numberOfClasses; i++) {
            cls[i] = reader.readUnsignedShort();
        }
        return new NestMembersAttribute(cls);
    }

    @Override
    public String toString() {
        return Arrays.toString(this.classes);
    }
}
