package org.example;

import org.example.model.ClassFile;

public class App {

    public static void main(String[] args) throws Exception {
        ClassParser classParser = new ClassParser();
        ClassFile classFile = classParser.parseFile("sample/Q13.class");
        System.out.println(classFile);
    }
}
