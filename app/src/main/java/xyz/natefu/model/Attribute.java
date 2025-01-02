package xyz.natefu.model;

import xyz.natefu.util.Constants;
import xyz.natefu.model.attributes.AttributeInfo;
import xyz.natefu.model.attributes.CodeAtt;
import xyz.natefu.model.attributes.DefaultAtt;
import xyz.natefu.model.constantpool.ConstantPool;
import xyz.natefu.model.constantpool.ConstantUtf8;
import xyz.natefu.util.StringUtils;

/**
 * @param constantPool constantPool here only for toString. not sure if it is still worth it
 */
public record Attribute(ConstantPool constantPool,
                        short attributeIndex,
                        AttributeInfo attributeInfo) {

    public static Attribute getInstance(short attributeIndex, byte[] attributeInfo, ConstantPool constantPool)
            throws IllegalByteCodeException {

        var str = constantPool.get(attributeIndex);
        if (!(str instanceof ConstantUtf8)) {
            throw new IllegalByteCodeException(Constants.BAD_ATTRIBUTE_INDEX);
        }
        var utf = (ConstantUtf8) constantPool.get(attributeIndex);

        AttributeInfo attributeInfoTemp = switch (utf.getData()) {
            case "Code" -> CodeAtt.getInstance(attributeInfo, constantPool);
            default -> new DefaultAtt(attributeInfo);
        };

        return new Attribute(constantPool, attributeIndex, attributeInfoTemp);
    }

    @Override
    public String toString() {
        return toString(2);
    }

    public String toString(int level) {
        var sb = new StringBuilder();
        if (level == 2) {
            sb.append("\t\t");
        } else if (level == 3) {
            sb.append("\t\t\t\t");
        } else {
            sb.append("\t");
        }
        sb.append("Attribute:").append("\n");
        if (level == 2) {
            sb.append("\t\t");
        } else if (level == 3) {
            sb.append("\t\t\t\t");
        } else {
            sb.append("\t");
        }
        sb.append("\tattributeIndex: #").append(this.attributeIndex).append(" ")
                .append(StringUtils.getUtf8(this.attributeIndex, this.constantPool)).append("\n");
        if (level == 2) {
            sb.append("\t\t");
        } else if (level == 3) {
            sb.append("\t\t\t\t");
        } else {
            sb.append("\t");
        }
        sb.append("\tattributeInfo: ");
        if (this.attributeInfo instanceof CodeAtt c) {
            sb.append(c).append("\n");
        } else {
            sb.append(this.attributeInfo).append("\n");
        }

        return sb.toString();
    }
}
