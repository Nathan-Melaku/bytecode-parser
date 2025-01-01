package xyz.natefu.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccessFlagTest {

    @Test
    void collectClassFlags() {
        var flags = new byte[] { (byte) 0xF6 , (byte) 0x31};
        var set = AccessFlag.collectAccessFlags(AccessFlag.Context.CLASS, flags);
        assertEquals(9, set.size());
        assertTrue(set.contains(AccessFlag.ACC_PUBLIC));
        assertTrue(set.contains(AccessFlag.ACC_FINAL));
        assertTrue(set.contains(AccessFlag.ACC_SUPER));
        assertTrue(set.contains(AccessFlag.ACC_INTERFACE));
        assertTrue(set.contains(AccessFlag.ACC_ABSTRACT));
        assertTrue(set.contains(AccessFlag.ACC_SYNTHETIC));
        assertTrue(set.contains(AccessFlag.ACC_ANNOTATION));
        assertTrue(set.contains(AccessFlag.ACC_ENUM));
        assertTrue(set.contains(AccessFlag.ACC_MODULE));
    }

    @Test
    void collectInnerClassFlags() {
        var flags = new byte[] { (byte) 0x76 , (byte) 0x1F};
        var set = AccessFlag.collectAccessFlags(AccessFlag.Context.INNER_CLASS, flags);
        assertEquals(10, set.size());
        assertTrue(set.contains(AccessFlag.ACC_PUBLIC));
        assertTrue(set.contains(AccessFlag.ACC_PRIVATE));
        assertTrue(set.contains(AccessFlag.ACC_PROTECTED));
        assertTrue(set.contains(AccessFlag.ACC_STATIC));
        assertTrue(set.contains(AccessFlag.ACC_FINAL));
        assertTrue(set.contains(AccessFlag.ACC_INTERFACE));
        assertTrue(set.contains(AccessFlag.ACC_ABSTRACT));
        assertTrue(set.contains(AccessFlag.ACC_SYNTHETIC));
        assertTrue(set.contains(AccessFlag.ACC_ANNOTATION));
        assertTrue(set.contains(AccessFlag.ACC_ENUM));
    }

    @Test
    void collectMethodFlags() {
        var flags = new byte[] {(byte) 0x1D, (byte) 0xFF};
        var set = AccessFlag.collectAccessFlags(AccessFlag.Context.METHOD, flags);
        assertEquals(12, set.size());
        assertTrue(set.contains(AccessFlag.ACC_PUBLIC));
        assertTrue(set.contains(AccessFlag.ACC_PRIVATE));
        assertTrue(set.contains(AccessFlag.ACC_PROTECTED));
        assertTrue(set.contains(AccessFlag.ACC_STATIC));
        assertTrue(set.contains(AccessFlag.ACC_FINAL));
        assertTrue(set.contains(AccessFlag.ACC_SYNCHRONIZED));
        assertTrue(set.contains(AccessFlag.ACC_BRIDGE));
        assertTrue(set.contains(AccessFlag.ACC_VARARGS));
        assertTrue(set.contains(AccessFlag.ACC_NATIVE));
        assertTrue(set.contains(AccessFlag.ACC_ABSTRACT));
        assertTrue(set.contains(AccessFlag.ACC_STRICT));
        assertTrue(set.contains(AccessFlag.ACC_SYNTHETIC));
    }

    @Test
    void collectMethodParameterFlags() {
        var flags = new byte[] {(byte) 0x90, (byte) 0x10};
        var set = AccessFlag.collectAccessFlags(AccessFlag.Context.METHOD_PARAMETER, flags);
        assertEquals(3, set.size());
        assertTrue(set.contains(AccessFlag.ACC_FINAL));
        assertTrue(set.contains(AccessFlag.ACC_SYNTHETIC));
        assertTrue(set.contains(AccessFlag.ACC_MANDATED));
    }

    @Test
    void collectFieldFlags() {
        var flags = new byte[] {(byte) 0x1D, (byte) 0xFF};
        var set = AccessFlag.collectAccessFlags(AccessFlag.Context.FIELD, flags);
        assertEquals(9, set.size());
        assertTrue(set.contains(AccessFlag.ACC_PUBLIC));
        assertTrue(set.contains(AccessFlag.ACC_PRIVATE));
        assertTrue(set.contains(AccessFlag.ACC_PROTECTED));
        assertTrue(set.contains(AccessFlag.ACC_STATIC));
        assertTrue(set.contains(AccessFlag.ACC_FINAL));
        assertTrue(set.contains(AccessFlag.ACC_VOLATILE));
        assertTrue(set.contains(AccessFlag.ACC_TRANSIENT));
        assertTrue(set.contains(AccessFlag.ACC_SYNTHETIC));
        assertTrue(set.contains(AccessFlag.ACC_ENUM));
    }

    @Test
    void collectModuleFlags() {
        var flags = new byte[] {(byte) 0x90, (byte) 0x20};
        var set = AccessFlag.collectAccessFlags(AccessFlag.Context.MODULE, flags);
        assertEquals(3, set.size());
        assertTrue(set.contains(AccessFlag.ACC_OPEN));
        assertTrue(set.contains(AccessFlag.ACC_SYNTHETIC));
        assertTrue(set.contains(AccessFlag.ACC_MANDATED));
    }

    @Test
    void collectModuleExportsFlags() {
        var flags = new byte[] {(byte) 0x90, (byte) 0x00};
        var set = AccessFlag.collectAccessFlags(AccessFlag.Context.MODULE_EXPORTS, flags);
        assertEquals(2, set.size());
        assertTrue(set.contains(AccessFlag.ACC_SYNTHETIC));
        assertTrue(set.contains(AccessFlag.ACC_MANDATED));
    }

    @Test
    void collectModuleOpensFlags() {
        var flags = new byte[] {(byte) 0x90, (byte) 0x00};
        var set = AccessFlag.collectAccessFlags(AccessFlag.Context.MODULE_OPENS, flags);
        assertEquals(2, set.size());
        assertTrue(set.contains(AccessFlag.ACC_SYNTHETIC));
        assertTrue(set.contains(AccessFlag.ACC_MANDATED));
    }

    @Test
    void collectModuleRequiresFlags() {
        var flags = new byte[] {(byte) 0x90, (byte) 0x60};
        var set = AccessFlag.collectAccessFlags(AccessFlag.Context.MODULE_REQUIRES, flags);
        assertEquals(4, set.size());
        assertTrue(set.contains(AccessFlag.ACC_SYNTHETIC));
        assertTrue(set.contains(AccessFlag.ACC_MANDATED));
        assertTrue(set.contains(AccessFlag.ACC_TRANSITIVE));
        assertTrue(set.contains(AccessFlag.ACC_STATIC_PHASE));
    }
}

