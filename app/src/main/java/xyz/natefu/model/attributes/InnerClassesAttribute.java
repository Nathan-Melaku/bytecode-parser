package xyz.natefu.model.attributes;

import xyz.natefu.ClassReader;
import xyz.natefu.model.AccessFlag;

import java.io.IOException;
import java.util.EnumSet;

public record InnerClassesAttribute(Clazz[] classes) implements AttributeInfo {
    public static InnerClassesAttribute getInstance(ClassReader reader) throws IOException {
        int numberOfClasses = reader.readUnsignedShort();
        Clazz[] clazzes = new Clazz[numberOfClasses];
        for (int i = 0; i < numberOfClasses; i++) {
            var innerClassInfoIndex = reader.readUnsignedShort();
            var outerClassInfoIndex = reader.readUnsignedShort();
            var innerNameIndex = reader.readUnsignedShort();
            var innerClassAccessFlags = reader.readUnsignedShort();
            clazzes[i] = new Clazz(innerClassInfoIndex,
                    outerClassInfoIndex,
                    innerNameIndex,
                    AccessFlag.collectAccessFlags(AccessFlag.Context.INNER_CLASS, innerClassAccessFlags));
        }
        return new InnerClassesAttribute(clazzes);
    }
    public record Clazz(int innerClassInfoIndex,
                        int outerClassInfoIndex,
                        int innerNameIndex,
                        EnumSet<AccessFlag> innerClassAccessFlags) {}
}
