package xyz.natefu.model.attributes;

import xyz.natefu.ClassReader;

import java.io.IOException;

public record StackMapTableAttribute() implements AttributeInfo {

    public static StackMapTableAttribute getInstance(ClassReader reader, int length) throws IOException {
        var numberOfEntries = reader.readUnsignedShort();
        for (int i = 0; i < numberOfEntries; i++) {
            var frameType = reader.readUnsignedByte();
            if (inRangeInclusive(frameType, 0, 63)) {
                System.out.println("SAME");
            } else if (inRangeInclusive(frameType, 64, 127)) {
                System.out.println("SAME_LOCALS_1_STACK_ITEM");
                var verificationTag = reader.readUnsignedByte();
                System.out.println("skip " + skipLen(verificationTag) + " bytes for tag " + verificationTag + ".");
                reader.skipBytes(skipLen(verificationTag));
            } else if (frameType == 247) {
                var offsetDelta = reader.readUnsignedShort();
                System.out.println("SAME_LOCALS_1_STACK_ITEM_EXTENDED, offset_delta: " + offsetDelta);
                var verificationTag = reader.readUnsignedByte();
                System.out.println("skip " + skipLen(verificationTag) + " bytes for tag: " + verificationTag + ".");
                reader.skipBytes(skipLen(verificationTag));
            } else if (inRangeInclusive(frameType, 248, 250)) {
                var offsetDelta = reader.readUnsignedShort();
                System.out.println("CHOP: offset_delta: " + offsetDelta);
            } else if (frameType == 251) {
                var offsetDelta = reader.readUnsignedShort();
                System.out.println("SAME_FRAME_EXTENDED: offset_delta: " + offsetDelta);
            } else if (inRangeInclusive(frameType, 252, 254)) {
                // append
                var offsetDelta = reader.readUnsignedShort();
                System.out.println("APPEND: offset_delta: " + offsetDelta);
                var locals = frameType - 251;
                for (int j = 0; j < locals; j++) {
                    var verificationTag = reader.readUnsignedByte();
                    System.out.println("skip " + skipLen(verificationTag) + " bytes for tag: " + verificationTag + ".");
                    reader.skipBytes(skipLen(verificationTag));
                }
            } else if (frameType == 255) {
                // full
                var offsetDelta = reader.readUnsignedShort();
                System.out.println("FULL_FRAME: offset_delta: " + offsetDelta);
                var numOfLocals = reader.readUnsignedShort();
                for (int j = 0; j < numOfLocals; j++) {
                    var verificationTag = reader.readUnsignedByte();
                    System.out.println("skip " + skipLen(verificationTag) + " bytes for tag: " + verificationTag + ".");
                    reader.skipBytes(skipLen(verificationTag));
                }
                var numOfStackItems = reader.readUnsignedShort();
                for (int j = 0; j < numOfStackItems; j++) {
                    var verificationTag = reader.readUnsignedByte();
                    System.out.println("skip " + skipLen(verificationTag) + " bytes for tag: " + verificationTag + ".");
                    reader.skipBytes(skipLen(verificationTag));
                }
            }
        }
        return new StackMapTableAttribute();
    }

    private static boolean inRangeInclusive(int x, int min, int max) {
        return min <= x && x <= max;
    }

    private static int skipLen(int tag) throws IOException{
        return switch (tag){
            case 0,1,2,3,4,5,6 -> 0;
            case 7,8 -> 2;
            default -> throw new IOException("what is this");
        };
    }
}
