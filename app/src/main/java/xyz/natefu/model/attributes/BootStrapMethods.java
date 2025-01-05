package xyz.natefu.model.attributes;

import xyz.natefu.ClassReader;

import java.io.IOException;
import java.util.Arrays;

public record BootStrapMethods (
        BootStrapMethod[] bootStrapMethods
) implements AttributeInfo{

    public static BootStrapMethods getInstance(ClassReader reader) throws IOException {
        reader.skipBytes(4);
        int numBootStrapMethods = reader.readUnsignedShort();
        BootStrapMethod[] bootStrapMethods = new BootStrapMethod[numBootStrapMethods];
        for (int i = 0; i < numBootStrapMethods; i++) {
            int methodRef = reader.readUnsignedShort();
            int numArguments = reader.readUnsignedShort();
            int[] arguments = new int[numArguments];
            for (int j = 0; j < numArguments; j++) {
                arguments[j] = reader.readUnsignedShort();
            }
            bootStrapMethods[i] = new BootStrapMethod(methodRef, numArguments, arguments);
        }
        return new BootStrapMethods(bootStrapMethods);
    }
    @Override
    public String toString() {
        return "bootStrap Methods: " +  Arrays.toString(bootStrapMethods);
    }

    public record BootStrapMethod(int methodRef, int numArguments, int[] arguments) {
        @Override
        public String toString() {
            return "[ " + "methodRef=#"  + methodRef + " numArguments=" +
                   numArguments + " arguments="+ Arrays.toString(arguments) + "]";
        }
    }
}
