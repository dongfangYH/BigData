package com.example.bytecode;


import com.example.bytecode.type.ClassFile;
import com.example.bytecode.util.ClassFileAnalyser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class ClassFileAnalyserMain {

    public static void main(String[] args) throws Exception{
        ByteBuffer codeBuf = readFile("/home/henry/aaa.class");
        ClassFile classFile = ClassFileAnalyser.analysis(codeBuf);
        System.out.println(classFile.getMagic().toHexString());
    }

    private static ByteBuffer readFile(String filePath) throws Exception{
        File file = new File(filePath);
        if (!file.exists()){
            throw new FileNotFoundException(filePath);
        }
        InputStream in = new FileInputStream(file);
        byte[] bytes = new byte[4096];
        int readBytes = in.read(bytes);
        if (readBytes <= 0){
            throw new RuntimeException("can not read file : " + filePath);
        }
        return ByteBuffer.wrap(bytes, 0, readBytes);
    }
}
