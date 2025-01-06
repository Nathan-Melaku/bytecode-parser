package xyz.natefu.model.attributes;

import xyz.natefu.ClassReader;
import xyz.natefu.model.IllegalByteCodeException;

import java.io.IOException;
import java.util.Arrays;

public record StackMapTableAttribute(StackMapFrame[] entries) implements AttributeInfo {

    public static StackMapTableAttribute getInstance(ClassReader reader) throws IOException {
        var numberOfEntries = reader.readUnsignedShort();
        StackMapFrame[] frames = new StackMapFrame[numberOfEntries];
        for (int i = 0; i < numberOfEntries; i++) {
            var frameType = reader.readUnsignedByte();
            if (inRangeInclusive(frameType, 0, 63)) {
                frames[i] = new SameFrame();
            } else if (inRangeInclusive(frameType, 64, 127)) {
                var info = VerificationTypeInfo.getInstance(reader);
                frames[i] = new SameLocals1StackItemFrame(info);
            } else if (frameType == 247) {
                var offsetDelta = reader.readUnsignedShort();
                var info = VerificationTypeInfo.getInstance(reader);
                frames[i] = new SameLocals1StackItemFrameExtended(offsetDelta, info);
            } else if (inRangeInclusive(frameType, 248, 250)) {
                var offsetDelta = reader.readUnsignedShort();
                frames[i] = new ChopFrame(offsetDelta);
            } else if (frameType == 251) {
                var offsetDelta = reader.readUnsignedShort();
                frames[i] = new SameFrameExtended(offsetDelta);
            } else if (inRangeInclusive(frameType, 252, 254)) {
                var offsetDelta = reader.readUnsignedShort();
                var locals = frameType - 251;
                var infos = new VerificationTypeInfo[locals];
                for (int j = 0; j < locals; j++) {
                    var info = VerificationTypeInfo.getInstance(reader);
                    infos[j] = info;
                }
                frames[i] = new AppendFrame(offsetDelta, infos);
            } else if (frameType == 255) {
                var offsetDelta = reader.readUnsignedShort();
                var numOfLocals = reader.readUnsignedShort();
                var locals = new VerificationTypeInfo[numOfLocals];
                for (int j = 0; j < numOfLocals; j++) {
                    var info = VerificationTypeInfo.getInstance(reader);
                    locals[j] = info;
                }
                var numOfStackItems = reader.readUnsignedShort();
                var stack = new VerificationTypeInfo[numOfStackItems];
                for (int j = 0; j < numOfStackItems; j++) {
                    var info = VerificationTypeInfo.getInstance(reader);
                    stack[j] = info;
                }
                frames[i] = new FullFrame(offsetDelta, locals, stack);
            }
        }
        return new StackMapTableAttribute(frames);
    }

    private static boolean inRangeInclusive(int x, int min, int max) {
        return min <= x && x <= max;
    }
    
    public sealed interface StackMapFrame permits SameFrame, SameLocals1StackItemFrame, SameLocals1StackItemFrameExtended,
    ChopFrame, SameFrameExtended, AppendFrame, FullFrame {}

    public static final class SameFrame implements StackMapFrame {}
    public record SameLocals1StackItemFrame(VerificationTypeInfo verificationTypeInfo) implements StackMapFrame {}
    public record SameLocals1StackItemFrameExtended(int offsetDelta, VerificationTypeInfo verificationTypeInfo)
            implements StackMapFrame {}
    public record ChopFrame(int offsetDelta) implements StackMapFrame {}
    public record SameFrameExtended(int offsetDelta) implements StackMapFrame {}
    public record AppendFrame(int offsetDelta, VerificationTypeInfo[] verificationTypeInfos)
            implements StackMapFrame {}
    public record FullFrame(int offsetDelta,
                     VerificationTypeInfo[] locals,
                     VerificationTypeInfo[] stack) implements StackMapFrame {}

    sealed static class VerificationTypeInfo permits TopVariableInfo, IntegerVariableInfo, FloatVariableInfo,
            LongVariableInfo, DoubleVariableInfo, NullVariableInfo, UninitializedThisVariableInfo,
            ObjectVariableInfo, UninitializedVariableInfo {
        final static int ITEM_Top = 0;
        final static int ITEM_Integer= 1;
        final static int ITEM_Float = 2;
        final static int ITEM_Double = 3;
        final static int ITEM_Long = 4;
        final static int ITEM_Null = 5;
        final static int ITEM_UninitializedThis = 6;
        final static int ITEM_Object = 7;
        final static int ITEM_Uninitialized = 8;

        static VerificationTypeInfo getInstance(ClassReader reader) throws IOException {
            var verificationTag = reader.readUnsignedByte();
            return switch (verificationTag) {
                case ITEM_Top -> new TopVariableInfo();
                case ITEM_Integer -> new IntegerVariableInfo();
                case ITEM_Float -> new FloatVariableInfo();
                case ITEM_Double -> new DoubleVariableInfo();
                case ITEM_Long -> new LongVariableInfo();
                case ITEM_Null -> new NullVariableInfo();
                case ITEM_UninitializedThis -> new UninitializedThisVariableInfo();
                case ITEM_Object -> new ObjectVariableInfo(reader);
                case ITEM_Uninitialized -> new UninitializedVariableInfo(reader);
                default -> throw new IllegalByteCodeException("Unknown VerificationTag: " + verificationTag);
            };
        }
    }

    static final class TopVariableInfo extends VerificationTypeInfo {}

    static final class IntegerVariableInfo extends VerificationTypeInfo {}

    static final class FloatVariableInfo extends VerificationTypeInfo {}

    static final class DoubleVariableInfo extends VerificationTypeInfo {}

    static final class LongVariableInfo extends VerificationTypeInfo {}

    static final class NullVariableInfo extends VerificationTypeInfo {}

    static final class UninitializedThisVariableInfo extends VerificationTypeInfo {}

    static final class ObjectVariableInfo extends VerificationTypeInfo {
        int cPoolIndex;
        ObjectVariableInfo (ClassReader reader) throws IOException {
            this.cPoolIndex = reader.readUnsignedShort();
        }
    }

    static final class UninitializedVariableInfo extends VerificationTypeInfo {
        int offset;
        UninitializedVariableInfo(ClassReader reader) throws IOException {
            this.offset = reader.readUnsignedShort();
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(entries);
    }
}
