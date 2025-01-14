package xyz.natefu.util;

import xyz.natefu.model.Attribute;
import xyz.natefu.model.constantpool.ConstantClass;
import xyz.natefu.model.constantpool.ConstantPool;
import xyz.natefu.model.constantpool.ConstantString;
import xyz.natefu.model.constantpool.ConstantUtf8;

/**
 * StringUtils
 */
public class StringUtils {

    public static String getClassName(int index, ConstantPool constantPool) {
        var cls = (ConstantClass) constantPool.get(index);
        var utf = (ConstantUtf8) constantPool.get(cls.getNameIndex());
        return utf.getData();
    }

    public static String getString(int index, ConstantPool constantPool) {
        var str = (ConstantString) constantPool.get(index);
        var utf = (ConstantUtf8) constantPool.get(str.getStringIndex());
        return utf.getData();
    }

    public static String getUtf8(int index, ConstantPool constantPool) {
        var utf = (ConstantUtf8) constantPool.get(index);
        return utf.getData();
    }

    public static String bufToStr(byte[] bytes) {
        var sb = new StringBuilder();
        sb.append("[ ");
        for (var b : bytes) {
            sb.append(String.format("%02X ", b));
        }
        sb.append("]");
        return sb.toString();
    }

    public static String attrsToStr(Attribute[] attributes) {
        var sb = new StringBuilder();
        for (var a : attributes) {
            sb.append(a);
        }
        return sb.toString();
    }
}
