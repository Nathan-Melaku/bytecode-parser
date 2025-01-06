package xyz.natefu.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccessFlagTest {

    @Test
    void collectClassFlags() {
        var set = AccessFlag.collectAccessFlags(AccessFlag.Context.CLASS, 0xF631);
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
        var set = AccessFlag.collectAccessFlags(AccessFlag.Context.INNER_CLASS, 0x761F);
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
        var set = AccessFlag.collectAccessFlags(AccessFlag.Context.METHOD, 0x1DFF);
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
        var set = AccessFlag.collectAccessFlags(AccessFlag.Context.METHOD_PARAMETER, 0x9010);
        assertEquals(3, set.size());
        assertTrue(set.contains(AccessFlag.ACC_FINAL));
        assertTrue(set.contains(AccessFlag.ACC_SYNTHETIC));
        assertTrue(set.contains(AccessFlag.ACC_MANDATED));
    }

    @Test
    void collectFieldFlags() {
        var set = AccessFlag.collectAccessFlags(AccessFlag.Context.FIELD, 0x50DF);
        System.out.println(set);
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
        var set = AccessFlag.collectAccessFlags(AccessFlag.Context.MODULE, 0x9020);
        assertEquals(3, set.size());
        assertTrue(set.contains(AccessFlag.ACC_OPEN));
        assertTrue(set.contains(AccessFlag.ACC_SYNTHETIC));
        assertTrue(set.contains(AccessFlag.ACC_MANDATED));
    }

    @Test
    void collectModuleExportsFlags() {
        var set = AccessFlag.collectAccessFlags(AccessFlag.Context.MODULE_EXPORTS, 0x9000);
        assertEquals(2, set.size());
        assertTrue(set.contains(AccessFlag.ACC_SYNTHETIC));
        assertTrue(set.contains(AccessFlag.ACC_MANDATED));
    }

    @Test
    void collectModuleOpensFlags() {
        var set = AccessFlag.collectAccessFlags(AccessFlag.Context.MODULE_OPENS, 0x9000);
        assertEquals(2, set.size());
        assertTrue(set.contains(AccessFlag.ACC_SYNTHETIC));
        assertTrue(set.contains(AccessFlag.ACC_MANDATED));
    }

    @Test
    void collectModuleRequiresFlags() {
        var set = AccessFlag.collectAccessFlags(AccessFlag.Context.MODULE_REQUIRES, 0x9060);
        assertEquals(4, set.size());
        assertTrue(set.contains(AccessFlag.ACC_SYNTHETIC));
        assertTrue(set.contains(AccessFlag.ACC_MANDATED));
        assertTrue(set.contains(AccessFlag.ACC_TRANSITIVE));
        assertTrue(set.contains(AccessFlag.ACC_STATIC_PHASE));
    }
}

