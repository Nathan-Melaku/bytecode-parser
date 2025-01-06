package xyz.natefu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.natefu.model.AccessFlag;
import xyz.natefu.model.Attribute;
import xyz.natefu.model.ClassFile;
import xyz.natefu.model.attributes.*;
import xyz.natefu.model.constantpool.ConstantClass;
import xyz.natefu.model.constantpool.ConstantUtf8;
import xyz.natefu.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ClassFileReadingTest {
    ClassLoader classLoader = getClass().getClassLoader();

    @Test
    @DisplayName("Should parse empty class")
    void shouldParseEmptyClass(){
        // GIVEN
        try (var inputStream = classLoader.getResourceAsStream("samples/EmptyClass.class")) {
            // WHEN
            var classFile = ClassFile.fromInputStream(inputStream);
            var magic = classFile.magic();
            var minorVersion = classFile.minorVersion();
            var majorVersion = classFile.majorVersion();
            var constantPool = classFile.constantPool();
            var accessFlags = classFile.accessFlags();
            var thisClass = classFile.thisClass();
            var superClass = classFile.superClass();
            var interfaces = classFile.interfaces();
            var fields = classFile.fields();
            var methods = classFile.methods();
            var attributes = classFile.attributes();

            // THEN
            assertEquals(0xCAFEBABE, magic);
            assertEquals((short) 0, minorVersion);
            assertEquals((short) 67, majorVersion);
            assertEquals(12, constantPool.getLength());

            assertEquals(1,accessFlags.size());
            assertTrue(accessFlags.contains(AccessFlag.ACC_SUPER));

            assertInstanceOf(ConstantClass.class, constantPool.get(thisClass));
            assertEquals("EmptyClass", StringUtils.getClassName(thisClass, constantPool));

            assertInstanceOf(ConstantClass.class, constantPool.get(superClass));
            assertEquals("java/lang/Object", StringUtils.getClassName(superClass, constantPool));

            assertEquals(0, interfaces.length);
            assertEquals(0, fields.length);

            assertEquals(1, methods.length);
            assertTrue(methods[0].accessFlags().isEmpty());
            assertEquals("<init>",StringUtils.getUtf8(methods[0].nameIndex(), constantPool));
            assertEquals("()V", StringUtils.getUtf8(methods[0].descriptorIndex(), constantPool));
            assertEquals(1, methods[0].attributes().length);
            var code = methods[0].attributes()[0];
            assertEquals("Code", StringUtils.getUtf8(code.attributeIndex(), constantPool));
            assertInstanceOf(CodeAttribute.class, code.attributeInfo());
            var codeAtt = (CodeAttribute) code.attributeInfo();
            assertEquals(1, codeAtt.maxStack());
            assertEquals(1, codeAtt.maxLocals());
            assertEquals(0, codeAtt.exceptionTable().length);
            assertEquals(1, codeAtt.attributes().length);
            assertEquals("LineNumberTable", StringUtils.getUtf8(
                    codeAtt.attributes()[0].attributeIndex(), constantPool));
            assertEquals(1, attributes.length);
            assertInstanceOf(ConstantUtf8.class, constantPool.get(attributes[0].attributeIndex()));
            assertEquals("SourceFile", StringUtils.getUtf8(attributes[0].attributeIndex(), constantPool));
        } catch (IOException e) {
            // should never happen in a test.
            fail();
        }
    }
    @Test
    @DisplayName("Should Parse methods")
    void shouldParseClassWithOneMethod(){
        // GIVEN
        try (var inputStream = classLoader.getResourceAsStream("samples/ClassWithMain.class")) {
            // WHEN
            var classFile = ClassFile.fromInputStream(inputStream);
            var constantPool = classFile.constantPool();
            var methods = classFile.methods();

            // THEN
            assertEquals(2, methods.length);
            assertEquals(2, methods[1].accessFlags().size());
            assertTrue(methods[1].accessFlags().contains(AccessFlag.ACC_PUBLIC));
            assertTrue(methods[1].accessFlags().contains(AccessFlag.ACC_STATIC));
            assertEquals("main", StringUtils.getUtf8(methods[1].nameIndex(), constantPool));
            assertEquals("([Ljava/lang/String;)V", StringUtils.getUtf8(
                    methods[1].descriptorIndex(), constantPool));
        } catch (IOException e) {
            // should never happen in a test.
            fail();
        }
    }

    @Test
    @DisplayName("Should Parse Constant value attribute")
    void shouldParseConstantValue() {
        // GIVEN
        try (var inputStream = classLoader.getResourceAsStream("samples/ConstantValue.class")) {
            // WHEN
            var classFile = ClassFile.fromInputStream(inputStream);
            var constantPool = classFile.constantPool();
            var fields = classFile.fields();

            assertEquals(1, fields.length);
            var attributes = fields[0].attributes();
            assertEquals(1, attributes.length);
            var attributeInfo = attributes[0].attributeInfo();
            assertInstanceOf(ConstantValueAttribute.class, attributeInfo);
            assertEquals("Constant",
                    StringUtils.getString(((ConstantValueAttribute) attributeInfo).valueIndex(), constantPool));
        } catch (IOException e) {
            // should never happen in a test.
            fail();
        }
    }

    @Test
    @DisplayName("Should Parse BootStrapMethods Attribute")
    void shouldParseBootStrapMethods() {
        // GIVEN
        try (var inputStream = classLoader.getResourceAsStream("samples/BootStrapMethods.class")) {
            // WHEN
            var classFile = ClassFile.fromInputStream(inputStream);
            var constantPool = classFile.constantPool();
            var attributes = classFile.attributes();

            var bootStrapAttribute = Arrays.stream(attributes).filter(attribute -> {
               if (constantPool.get(attribute.attributeIndex()) instanceof ConstantUtf8 utf8) {
                   return utf8.getData().equals(Attribute.BootstrapMethods);
               }
               return false;
            }).toArray();
            // THEN
            assertEquals(1, bootStrapAttribute.length);
            var bootStrapAttInfo = ((Attribute) bootStrapAttribute[0]).attributeInfo();
            assertInstanceOf(BootStrapMethodsAttribute.class, bootStrapAttInfo);
            BootStrapMethodsAttribute.BootStrapMethod[] bootStrapMethods = ((BootStrapMethodsAttribute) bootStrapAttInfo).bootStrapMethods();
            assertEquals(3, bootStrapMethods.length);
            var uniqueMethodRefs = Arrays.stream(bootStrapMethods)
                    .map(BootStrapMethodsAttribute.BootStrapMethod::methodRef)
                    .distinct();
            assertEquals(1, uniqueMethodRefs.count());
        } catch (IOException e) {
            // should never happen in a test.
            fail();
        }
    }

    @Test
    @DisplayName("should parse NestHost attributes")
    void shouldParseNestHostAttributes() {
        // GIVEN
        try (var inputStream = classLoader.getResourceAsStream("samples/NestedClass$FirstNestedClass.class")) {
            // WHEN
            var classFile = ClassFile.fromInputStream(inputStream);
            var constantPool = classFile.constantPool();
            var attributes = classFile.attributes();

            // THEN
            var nestHostFound = false;
            for (var att : attributes) {
                var info = att.attributeInfo();
                if (info instanceof NestHostAttribute(int hostClassIndex)){
                    var constantClass = constantPool.get(hostClassIndex);
                    assertInstanceOf(ConstantClass.class, constantClass);
                    var nestHostClassName = constantPool.get(((ConstantClass) constantClass).getNameIndex());
                    assertInstanceOf(ConstantUtf8.class, nestHostClassName);
                    assertEquals("NestedClass", ((ConstantUtf8) nestHostClassName).getData());
                    nestHostFound = true;
                }
            }
            assertTrue(nestHostFound);

        } catch (IOException e){
            // should never happen in a test.
            fail();
        }
    }

    @Test
    @DisplayName("should parse line number attribute")
    void shouldParseLineNumberAttribute() {
        // GIVEN
        try (var inputStream = classLoader.getResourceAsStream("samples/ConstantValue.class")) {
            // WHEN
            var classFile = ClassFile.fromInputStream(inputStream);
            var methods = classFile.methods();
            // THEN
            assertTrue(methods.length > 0);
            for (var method : methods) {
                var lineNumberAttrFound = false;
                var attributes = method.attributes();
                for (var attribute : attributes) {
                    if (attribute.attributeInfo() instanceof CodeAttribute(_, _, _, _, Attribute[] codeAttributes)) {
                        lineNumberAttrFound = Arrays.stream(codeAttributes)
                                .anyMatch(attribute1 -> attribute1.attributeInfo() instanceof LineNumberTableAttribute);
                    }
                }
                assertTrue(lineNumberAttrFound);
            }
        } catch (IOException e){
            // should never happen in a test.
            fail();
        }
    }
}
