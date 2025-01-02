package xyz.natefu;

import org.junit.jupiter.api.Test;
import xyz.natefu.model.AccessFlag;
import xyz.natefu.model.attributes.CodeAtt;
import xyz.natefu.model.constantpool.ConstantClass;
import xyz.natefu.model.constantpool.ConstantUtf8;
import xyz.natefu.util.StringUtils;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ClassParserTest {
    ClassParser subject = new ClassParser();

    @Test
    void shouldParseEmptyClass(){
        // GIVEN
        var classLoader = getClass().getClassLoader();
        try (var inputStream = classLoader.getResourceAsStream("samples/EmptyClass.class")) {
            assert inputStream != null;
            // WHEN
            var classFile = subject.parseFile(inputStream);
            var magic = classFile.getMagic();
            var minorVersion = classFile.getMinorVersion();
            var majorVersion = classFile.getMajorVersion();
            var constantPool = classFile.getConstantPool();
            var accessFlags = classFile.getAccessFlags();
            var thisClass = classFile.getThisClass();
            var superClass = classFile.getSuperClass();
            var interfaces = classFile.getInterfaces();
            var fields = classFile.getFields();
            var methods = classFile.getMethods();
            var attributes = classFile.getAttributes();

            // THEN
            assertEquals(4, magic.length);
            assertEquals((byte) 0xCA, magic[0]);
            assertEquals((byte) 0xFE, magic[1]);
            assertEquals((byte) 0xBA, magic[2]);
            assertEquals((byte) 0xBE, magic[3]);
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
            assertInstanceOf(CodeAtt.class, code.attributeInfo());
            var codeAtt = (CodeAtt) code.attributeInfo();
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
            assertTrue(true);
        }
    }
    @Test
    void shouldParseClassWithOneMethod(){
        // GIVEN
        var classLoader = getClass().getClassLoader();
        try (var inputStream = classLoader.getResourceAsStream("samples/ClassWithMain.class")) {
            assert inputStream != null;
            // WHEN
            var classFile = subject.parseFile(inputStream);
            var constantPool = classFile.getConstantPool();
            var methods = classFile.getMethods();

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
            assertTrue(true);
        }
    }
}