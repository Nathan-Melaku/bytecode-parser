package xyz.natefu;

import java.nio.ByteBuffer;

import xyz.natefu.model.*;
import xyz.natefu.model.constantpool.ConstantPool;

class ClassParser {
    public ClassFile parseFile(String filename) throws Exception {
        var classLoader = getClass().getClassLoader();
        try (var sample = classLoader.getResourceAsStream(filename)) {
            var builder = new ClassFile.Builder();
            // read magic
            assert sample != null;
            byte[] read = sample.readNBytes(4);
            if (read.length != 4) {
                throw new IllegalArgumentException();
            }
            builder.magic(read);

            // read minor version
            read = sample.readNBytes(2);
            if (read.length != 2) {
                throw new IllegalArgumentException();
            }
            builder.minorVersion(ByteBuffer.wrap(read).getShort());

            // read major version
            read = sample.readNBytes(2);
            if (read.length != 2) {
                throw new IllegalArgumentException();
            }
            builder.majorVersion(ByteBuffer.wrap(read).getShort());

            // read constant pool count
            read = sample.readNBytes(2);
            if (read.length != 2) {
                throw new IllegalArgumentException();
            }
            short constantPoolLen = ByteBuffer.wrap(read).getShort();
            ConstantPool cp = new ConstantPool(constantPoolLen);
            // cp_info
            for (int j = 0; j < constantPoolLen - 1; j++) {
                read = sample.readNBytes(1);
                if (read.length != 1) {
                    throw new IllegalArgumentException();
                }
                var tag = (short) read[0];
                var bytesToRead = ConstantKind.expectedBytes(tag);
                if (bytesToRead == -1) {
                    var lenBuf = sample.readNBytes(2);
                    bytesToRead = ByteBuffer.wrap(lenBuf).getShort();
                }
                var bytesRead = sample.readNBytes(bytesToRead);
                if (bytesRead.length != bytesToRead) {
                    throw new IllegalArgumentException();
                }
                cp.add(tag, bytesRead);
            }
            builder.constantPool(cp);

            // read access_flags
            read = sample.readNBytes(2);
            if (read.length != 2) {
                throw new IllegalArgumentException();
            }
            builder.accessFlags(AccessFlag.collectAccessFlags(AccessFlag.Context.CLASS, read));

            // read this_class
            read = sample.readNBytes(2);
            if (read.length != 2) {
                throw new IllegalArgumentException();
            }
            builder.thisClass(ByteBuffer.wrap(read).getShort());

            // read super_class
            read = sample.readNBytes(2);
            if (read.length != 2) {
                throw new IllegalArgumentException();
            }
            builder.superClass(ByteBuffer.wrap(read).getShort());

            // read interfaces
            read = sample.readNBytes(2);
            if (read.length != 2) {
                throw new IllegalArgumentException();
            }
            var interfaceCount = ByteBuffer.wrap(read).getShort();
            short[] interfaces = new short[interfaceCount];
            for (int j = 0; j < interfaceCount; j++) {
                read = sample.readNBytes(2);
                if (read.length != 2) {
                    throw new IllegalArgumentException();
                }
                interfaces[j] = ByteBuffer.wrap(read).getShort();
            }
            builder.interfaces(interfaces);

            // read fields
            read = sample.readNBytes(2);
            if (read.length != 2) {
                throw new IllegalArgumentException();
            }
            var field_count = ByteBuffer.wrap(read).getShort();
            Field[] fields = new Field[field_count];
            for (int j = 0; j < field_count; j++) {
                var accFlagsBuf = sample.readNBytes(2);
                var nameIndex = sample.readNBytes(2);
                var descriptorIndex = sample.readNBytes(2);
                var attCountBuf = sample.readNBytes(2);
                if (accFlagsBuf.length != 2 || nameIndex.length != 2 ||
                        descriptorIndex.length != 2 || attCountBuf.length != 2) {
                    throw new IllegalArgumentException();
                }
                var accFlags = AccessFlag.collectAccessFlags(AccessFlag.Context.FIELD, accFlagsBuf);
                var attCount = ByteBuffer.wrap(attCountBuf).getShort();
                Attribute[] attributes = new Attribute[attCount];
                for (int k = 0; k < attCount; k++) {
                    var attIndexBuf = sample.readNBytes(2);
                    var attLenBuf = sample.readNBytes(4);
                    if (attIndexBuf.length != 2 || attLenBuf.length != 4) {
                        throw new IllegalArgumentException();
                    }
                    var attNameIndex = ByteBuffer.wrap(attIndexBuf).getShort();
                    var attLength = ByteBuffer.wrap(attLenBuf).getInt();
                    read = sample.readNBytes(attLength);
                    if (read.length != attLength) {
                        throw new IllegalArgumentException();
                    }
                    attributes[k] = new Attribute(attNameIndex, read, cp);
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
            read = sample.readNBytes(2);
            if (read.length != 2) {
                throw new IllegalArgumentException();
            }
            var methodCount = ByteBuffer.wrap(read).getShort();
            Method[] methods = new Method[methodCount];
            for (int j = 0; j < methodCount; j++) {
                var accFlagsBuf = sample.readNBytes(2);
                var nameIndex = sample.readNBytes(2);
                var descriptorIndex = sample.readNBytes(2);
                var attCountBuf = sample.readNBytes(2);
                if (accFlagsBuf.length != 2 || nameIndex.length != 2 ||
                        descriptorIndex.length != 2 || attCountBuf.length != 2) {
                    throw new IllegalArgumentException();
                }
                var attCount = ByteBuffer.wrap(attCountBuf).getShort();
                var accFlags = AccessFlag.collectAccessFlags(AccessFlag.Context.METHOD, accFlagsBuf);
                Attribute[] attributes = new Attribute[attCount];
                for (int k = 0; k < attCount; k++) {
                    var attIndexBuf = sample.readNBytes(2);
                    var attLenBuf = sample.readNBytes(4);
                    if (attIndexBuf.length != 2 || attLenBuf.length != 4) {
                        throw new IllegalArgumentException();
                    }
                    var attNameIndex = ByteBuffer.wrap(attIndexBuf).getShort();
                    var attLength = ByteBuffer.wrap(attLenBuf).getInt();
                    read = sample.readNBytes(attLength);
                    if (read.length != attLength) {
                        throw new IllegalArgumentException();
                    }
                    attributes[k] = new Attribute(attNameIndex, read, cp);
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
            read = sample.readNBytes(2);
            if (read.length != 2) {
                throw new IllegalArgumentException();
            }
            var attCount = ByteBuffer.wrap(read).getShort();
            Attribute[] attributes = new Attribute[attCount];
            for (int k = 0; k < attCount; k++) {
                var attIndexBuf = sample.readNBytes(2);
                var attLenBuf = sample.readNBytes(4);
                if (attIndexBuf.length != 2 || attLenBuf.length != 4) {
                    throw new IllegalArgumentException();
                }
                var attNameIndex = ByteBuffer.wrap(attIndexBuf).getShort();
                var attLength = ByteBuffer.wrap(attLenBuf).getInt();
                read = sample.readNBytes(attLength);
                if (read.length != attLength) {
                    throw new IllegalArgumentException();
                }
                attributes[k] = new Attribute(attNameIndex, read, cp);
            }
            builder.attributes(attributes);
            return builder.build();
        }
    }
}
