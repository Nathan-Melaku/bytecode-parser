package xyz.natefu.util;

import xyz.natefu.model.Attribute;
import xyz.natefu.model.constantpool.ConstantClass;
import xyz.natefu.model.constantpool.ConstantPool;
import xyz.natefu.model.constantpool.ConstantUtf8;

/**
 * StringUtils
 */
public class StringUtils {

    public static String getClassName(short index, ConstantPool constantPool) {
        var cls = (ConstantClass) constantPool.get(index);
        var utf = (ConstantUtf8) constantPool.get(cls.getNameIndex());
        return utf.getData();
    }

    public static String getUtf8(short index, ConstantPool constantPool) {
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

    public static String attsToStr(Attribute[] attributes, ConstantPool constantPool) {
        var sb = new StringBuilder();
        for (var a : attributes) {
            sb.append(a.toString((short) 2, constantPool));
        }
        return sb.toString();
    }
}
