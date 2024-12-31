package org.example.model.constantpool;

import org.example.model.ConstantKind;
import java.nio.charset.StandardCharsets;

public final class ConstantUtf8 extends ConstantPoolInfo {
    short tag = ConstantKind.CONSTANT_Utf8;
    String data;

    public ConstantUtf8(byte[] bytes) {
        this.data = new String(bytes, StandardCharsets.UTF_8);
    }

    public String getData() {
        return data;
    }

    public String toString() {
        return "Utf8\t\t\t[ " + data + " ]";
    }
}
