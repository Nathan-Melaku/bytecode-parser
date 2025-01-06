package xyz.natefu.model;

import xyz.natefu.ClassReader;
import xyz.natefu.model.attributes.*;
import xyz.natefu.util.Constants;
import xyz.natefu.model.constantpool.ConstantUtf8;

import java.io.IOException;

public record Attribute(int attributeIndex,
                        AttributeInfo attributeInfo) {
    public static final String AnnotationDefault        = "AnnotationDefault";
    public static final String BootstrapMethods         = "BootstrapMethods";
    public static final String CharacterRangeTable      = "CharacterRangeTable";
    public static final String Code                     = "Code";
    public static final String ConstantValue            = "ConstantValue";
    public static final String CompilationID            = "CompilationID";
    public static final String Deprecated               = "Deprecated";
    public static final String EnclosingMethod          = "EnclosingMethod";
    public static final String Exceptions               = "Exceptions";
    public static final String InnerClasses             = "InnerClasses";
    public static final String LineNumberTable          = "LineNumberTable";
    public static final String LocalVariableTable       = "LocalVariableTable";
    public static final String LocalVariableTypeTable   = "LocalVariableTypeTable";
    public static final String MethodParameters         = "MethodParameters";
    public static final String Module                   = "Module";
    public static final String ModuleHashes             = "ModuleHashes";
    public static final String ModuleMainClass          = "ModuleMainClass";
    public static final String ModulePackages           = "ModulePackages";
    public static final String ModuleResolution         = "ModuleResolution";
    public static final String ModuleTarget             = "ModuleTarget";
    public static final String NestHost                 = "NestHost";
    public static final String NestMembers              = "NestMembers";
    public static final String Record                   = "Record";
    public static final String RuntimeVisibleAnnotations = "RuntimeVisibleAnnotations";
    public static final String RuntimeInvisibleAnnotations = "RuntimeInvisibleAnnotations";
    public static final String RuntimeVisibleParameterAnnotations = "RuntimeVisibleParameterAnnotations";
    public static final String RuntimeInvisibleParameterAnnotations = "RuntimeInvisibleParameterAnnotations";
    public static final String RuntimeVisibleTypeAnnotations = "RuntimeVisibleTypeAnnotations";
    public static final String RuntimeInvisibleTypeAnnotations = "RuntimeInvisibleTypeAnnotations";
    public static final String PermittedSubclasses      = "PermittedSubclasses";
    public static final String Signature                = "Signature";
    public static final String SourceDebugExtension     = "SourceDebugExtension";
    public static final String SourceFile               = "SourceFile";
    public static final String SourceID                 = "SourceID";
    public static final String StackMap                 = "StackMap";
    public static final String StackMapTable            = "StackMapTable";
    public static final String Synthetic                = "Synthetic";

    public record Factory(ClassReader reader) {
        public Attribute getInstance()
                throws IllegalByteCodeException, IOException {
            var attributeIndex = reader.readUnsignedShort();
            var str = reader.getConstant(attributeIndex);

            if (!(str instanceof ConstantUtf8)) {
                throw new IllegalByteCodeException(Constants.BAD_ATTRIBUTE_INDEX);
            }
            var utf = (ConstantUtf8) reader.getConstant(attributeIndex);
            var length = reader.readInt();
            AttributeInfo attributeInfoTemp = switch (utf.getData()) {
                case Code -> CodeAttribute.getInstance(reader, this);
                case ConstantValue -> ConstantValueAttribute.getInstance(reader);
                case BootstrapMethods -> BootStrapMethodsAttribute.getInstance(reader);
                case NestHost -> NestHostAttribute.getInstance(reader);
                case LineNumberTable -> LineNumberTableAttribute.getInstance(reader);
                case SourceFile -> SourceFileAttribute.getInstance(reader);
                case StackMapTable -> StackMapTableAttribute.getInstance(reader);
                case NestMembers -> NestMembersAttribute.getInstance(reader);
                case InnerClasses -> InnerClassesAttribute.getInstance(reader);
                default -> new CustomAttribute(reader, utf.getData(), length);
            };

            return new Attribute(attributeIndex, attributeInfoTemp);
        }

        public Attribute[] readAttributes() throws IOException {
                var attCount = reader.readUnsignedShort();
                Attribute[] attributes = new Attribute[attCount];
                for (int k = 0; k < attCount; k++) {
                    attributes[k] = getInstance();
                }
                return attributes;
            }
        }

    @Override
    public String toString() {
        return "Attribute:" + "\n" +
               "\tattributeIndex: #" + this.attributeIndex + "\n" +
               "\tattributeInfo: " +
               this.attributeInfo + "\n";
    }
}
