package xyz.natefu;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import xyz.natefu.model.*;
import xyz.natefu.model.constantpool.ConstantPool;
import xyz.natefu.util.Constants;

class ClassParser {
    public ClassFile parseFile(InputStream inputStream) throws IllegalByteCodeException, IOException {
        var classLoader = getClass().getClassLoader();

            var builder = new ClassFile.Builder();
            // read magic
            assert inputStream != null;
            byte[] read = inputStream.readNBytes(4);
            if (read.length != 4) {
                throw new IllegalByteCodeException(Constants.BAD_MAGIC);
            }
            builder.magic(read);

            // read minor version
            read = inputStream.readNBytes(2);
            if (read.length != 2) {
                throw new IllegalByteCodeException(Constants.BAD_MINOR_VERSION);
            }
            builder.minorVersion(ByteBuffer.wrap(read).getShort());

            // read major version
            read = inputStream.readNBytes(2);
            if (read.length != 2) {
                throw new IllegalByteCodeException(Constants.BAD_MAJOR_VERSION);
            }
            builder.majorVersion(ByteBuffer.wrap(read).getShort());

            // read constant pool count
            read = inputStream.readNBytes(2);
            if (read.length != 2) {
                throw new IllegalByteCodeException(Constants.BAD_CONSTANT_POOL_COUNT);
            }
            short constantPoolLen = ByteBuffer.wrap(read).getShort();
            ConstantPool cp = new ConstantPool(constantPoolLen);
            // cp_info
            for (int j = 0; j < constantPoolLen - 1; j++) {
                read = inputStream.readNBytes(1);
                if (read.length != 1) {
                    throw new IllegalByteCodeException(Constants.BAD_CONSTANT_POOL_LENGTH);
                }
                var tag = (short) read[0];
                var bytesToRead = ConstantKind.expectedBytes(tag);
                if (bytesToRead == -1) {
                    var lenBuf = inputStream.readNBytes(2);
                    bytesToRead = ByteBuffer.wrap(lenBuf).getShort();
                }
                var bytesRead = inputStream.readNBytes(bytesToRead);
                if (bytesRead.length != bytesToRead) {
                    throw new IllegalByteCodeException(Constants.BAD_CONSTANT_POOL_VALUE);
                }
                cp.add(tag, bytesRead);
            }
            builder.constantPool(cp);

            // read access_flags
            read = inputStream.readNBytes(2);
            if (read.length != 2) {
                throw new IllegalByteCodeException(Constants.BAD_ACCESS_FLAG);
            }
            builder.accessFlags(AccessFlag.collectAccessFlags(AccessFlag.Context.CLASS, read));

            // read this_class
            read = inputStream.readNBytes(2);
            if (read.length != 2) {
                throw new IllegalByteCodeException(Constants.BAD_THIS_CLASS);
            }
            builder.thisClass(ByteBuffer.wrap(read).getShort());

            // read super_class
            read = inputStream.readNBytes(2);
            if (read.length != 2) {
                throw new IllegalByteCodeException(Constants.BAD_SUPER_CLASS);
            }
            builder.superClass(ByteBuffer.wrap(read).getShort());

            // read interfaces
            read = inputStream.readNBytes(2);
            if (read.length != 2) {
                throw new IllegalByteCodeException(Constants.BAD_INTERFACE_COUNT);
            }
            var interfaceCount = ByteBuffer.wrap(read).getShort();
            short[] interfaces = new short[interfaceCount];
            for (int j = 0; j < interfaceCount; j++) {
                read = inputStream.readNBytes(2);
                if (read.length != 2) {
                    throw new IllegalByteCodeException(Constants.BAD_INTERFACE);
                }
                interfaces[j] = ByteBuffer.wrap(read).getShort();
            }
            builder.interfaces(interfaces);

            // read fields
            read = inputStream.readNBytes(2);
            if (read.length != 2) {
                throw new IllegalByteCodeException(Constants.BAD_FIELD_COUNT);
            }
            var field_count = ByteBuffer.wrap(read).getShort();
            Field[] fields = new Field[field_count];
            for (int j = 0; j < field_count; j++) {
                var accFlagsBuf = inputStream.readNBytes(2);
                var nameIndex = inputStream.readNBytes(2);
                var descriptorIndex = inputStream.readNBytes(2);
                var attCountBuf = inputStream.readNBytes(2);
                if (accFlagsBuf.length != 2 || nameIndex.length != 2 ||
                        descriptorIndex.length != 2 || attCountBuf.length != 2) {
                    throw new IllegalByteCodeException(Constants.BAD_FIELD_PARAMETER);
                }
                var accFlags = AccessFlag.collectAccessFlags(AccessFlag.Context.FIELD, accFlagsBuf);
                var attCount = ByteBuffer.wrap(attCountBuf).getShort();
                Attribute[] attributes = new Attribute[attCount];
                for (int k = 0; k < attCount; k++) {
                    var attIndexBuf = inputStream.readNBytes(2);
                    var attLenBuf = inputStream.readNBytes(4);
                    if (attIndexBuf.length != 2 || attLenBuf.length != 4) {
                        throw new IllegalByteCodeException(Constants.BAD_ATTRIBUTE_PARAMETER);
                    }
                    var attNameIndex = ByteBuffer.wrap(attIndexBuf).getShort();
                    var attLength = ByteBuffer.wrap(attLenBuf).getInt();
                    read = inputStream.readNBytes(attLength);
                    if (read.length != attLength) {
                        throw new IllegalByteCodeException(Constants.BAD_ATTRIBUTE_INFO_LENGTH);
                    }
                    attributes[k] = Attribute.getInstance(attNameIndex, read, cp);
                }
                fields[j] = new Field(
                        cp,
                        accFlags,
                        ByteBuffer.wrap(nameIndex).getShort(),
                        ByteBuffer.wrap(descriptorIndex).getShort(),
                        attributes);
            }
            builder.fields(fields);

            // read methods
            read = inputStream.readNBytes(2);
            if (read.length != 2) {
                throw new IllegalByteCodeException(Constants.BAD_METHOD_COUNT);
            }
            var methodCount = ByteBuffer.wrap(read).getShort();
            Method[] methods = new Method[methodCount];
            for (int j = 0; j < methodCount; j++) {
                var accFlagsBuf = inputStream.readNBytes(2);
                var nameIndex = inputStream.readNBytes(2);
                var descriptorIndex = inputStream.readNBytes(2);
                var attCountBuf = inputStream.readNBytes(2);
                if (accFlagsBuf.length != 2 || nameIndex.length != 2 ||
                        descriptorIndex.length != 2 || attCountBuf.length != 2) {
                    throw new IllegalByteCodeException(Constants.BAD_METHOD_PARAMETER);
                }
                var attCount = ByteBuffer.wrap(attCountBuf).getShort();
                var accFlags = AccessFlag.collectAccessFlags(AccessFlag.Context.METHOD, accFlagsBuf);
                Attribute[] attributes = new Attribute[attCount];
                for (int k = 0; k < attCount; k++) {
                    var attIndexBuf = inputStream.readNBytes(2);
                    var attLenBuf = inputStream.readNBytes(4);
                    if (attIndexBuf.length != 2 || attLenBuf.length != 4) {
                        throw new IllegalByteCodeException(Constants.BAD_ATTRIBUTE_PARAMETER);
                    }
                    var attNameIndex = ByteBuffer.wrap(attIndexBuf).getShort();
                    var attLength = ByteBuffer.wrap(attLenBuf).getInt();
                    read = inputStream.readNBytes(attLength);
                    if (read.length != attLength) {
                        throw new IllegalByteCodeException(Constants.BAD_ATTRIBUTE_INFO_LENGTH);
                    }
                    attributes[k] = Attribute.getInstance(attNameIndex, read, cp);
                }
                methods[j] = new Method(
                        cp,
                        accFlags,
                        ByteBuffer.wrap(nameIndex).getShort(),
                        ByteBuffer.wrap(descriptorIndex).getShort(),
                        attributes);
            }
            builder.methods(methods);
            // read attributes
            read = inputStream.readNBytes(2);
            if (read.length != 2) {
                throw new IllegalByteCodeException(Constants.BAD_ATTRIBUTE_COUNT);
            }
            var attCount = ByteBuffer.wrap(read).getShort();
            Attribute[] attributes = new Attribute[attCount];
            for (int k = 0; k < attCount; k++) {
                var attIndexBuf = inputStream.readNBytes(2);
                var attLenBuf = inputStream.readNBytes(4);
                if (attIndexBuf.length != 2 || attLenBuf.length != 4) {
                    throw new IllegalByteCodeException(Constants.BAD_ATTRIBUTE_PARAMETER);
                }
                var attNameIndex = ByteBuffer.wrap(attIndexBuf).getShort();
                var attLength = ByteBuffer.wrap(attLenBuf).getInt();
                read = inputStream.readNBytes(attLength);
                if (read.length != attLength) {
                    throw new IllegalByteCodeException(Constants.BAD_ATTRIBUTE_INFO_LENGTH);
                }
                attributes[k] = Attribute.getInstance(attNameIndex, read, cp);
            }
            builder.attributes(attributes);
            return builder.build();
    }
}
