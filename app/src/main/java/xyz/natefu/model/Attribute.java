package xyz.natefu.model;

import xyz.natefu.model.attributes.AttributeInfo;
import xyz.natefu.model.attributes.CodeAtt;
import xyz.natefu.model.attributes.DefaultAtt;
import xyz.natefu.model.constantpool.ConstantPool;
import xyz.natefu.model.constantpool.ConstantUtf8;
import xyz.natefu.util.StringUtils;

public class Attribute {
    private final short attributeIndex;
    private final AttributeInfo attributeInfo;

    public Attribute(short attributeIndex, byte[] attributeInfo, ConstantPool constantPool) throws IllegalArgumentException {
        this.attributeIndex = attributeIndex;
        var str =  constantPool.get(attributeIndex);;

        if (!(str instanceof ConstantUtf8)){
            throw new IllegalArgumentException();
        }

        var utf = (ConstantUtf8)  constantPool.get(attributeIndex);

        switch (utf.getData()) {
            case "Code":
                this.attributeInfo = new CodeAtt(attributeInfo, constantPool);
                break;
            case "ConstantValue":
            default:
                this.attributeInfo = new DefaultAtt(attributeInfo);
                break;

        }
    }

    public String toString(short level, ConstantPool constantPool) {
        var sb = new StringBuilder();
        if(level == 2){
            sb.append("\t\t");
        } else if (level == 3) {
            sb.append("\t\t\t\t");
        } else {
            sb.append("\t");
        }
        sb.append("Attribute:").append("\n");
        if(level == 2){
            sb.append("\t\t");
        } else if (level == 3) {
            sb.append("\t\t\t\t");
        } else {
            sb.append("\t");
        }
        sb.append("\tattributeIndex: #").append(attributeIndex).append(" ")
            .append(StringUtils.getUtf8(attributeIndex, constantPool)).append("\n");
        if(level == 2){
            sb.append("\t\t");
        } else if (level == 3) {
            sb.append("\t\t\t\t");
        } else {
            sb.append("\t");
        }
        sb.append("\tattributeInfo: ");
        if (attributeInfo instanceof CodeAtt c) {
            sb.append(c.toString(constantPool)).append("\n");
        } else {
            sb.append(attributeInfo).append("\n");
        }

        return sb.toString();
    }
}
