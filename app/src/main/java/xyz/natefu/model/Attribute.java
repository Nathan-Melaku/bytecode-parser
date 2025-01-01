package xyz.natefu.model;

import xyz.natefu.model.attributes.AttributeInfo;
import xyz.natefu.model.attributes.CodeAtt;
import xyz.natefu.model.attributes.DefaultAtt;
import xyz.natefu.model.constantpool.ConstantPool;
import xyz.natefu.model.constantpool.ConstantUtf8;
import xyz.natefu.util.StringUtils;

public class Attribute {
    /* constantPool here only for toString. not sure if it is still worth it*/
    private final ConstantPool constantPool;
    private final short attributeIndex;
    private final AttributeInfo attributeInfo;

    public Attribute(short attributeIndex, byte[] attributeInfo, ConstantPool constantPool) throws IllegalArgumentException {
        this.constantPool = constantPool;
        this.attributeIndex = attributeIndex;
        var str =  this.constantPool.get(attributeIndex);

        if (!(str instanceof ConstantUtf8)){
            throw new IllegalArgumentException();
        }

        var utf = (ConstantUtf8) this.constantPool.get(attributeIndex);

        switch (utf.getData()) {
            case "Code":
                this.attributeInfo = new CodeAtt(attributeInfo, this.constantPool);
                break;
            case "ConstantValue":
            default:
                this.attributeInfo = new DefaultAtt(attributeInfo);
                break;

        }
    }

    @Override
    public String toString() {
        return toString( 2);
    }

    public String toString(int level) {
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
        sb.append("\tattributeIndex: #").append(this.attributeIndex).append(" ")
            .append(StringUtils.getUtf8(this.attributeIndex, this.constantPool)).append("\n");
        if(level == 2){
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
