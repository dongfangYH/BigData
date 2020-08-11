package com.example.design_pattern.structure.proxy;


import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.FileOutputStream;

public class GenerateProxyClass {

    public static void main(String[] args) throws Exception {
        byte[] bytes = ProxyGenerator.generateProxyClass("UserService$proxy", new Class[]{
                UserService.class
        });
        String fileName = System.getProperty("user.dir") + "/designPattern/target/UserService$proxy.class";
        File file = new File(fileName);
        FileOutputStream op = new FileOutputStream(file);
        op.write(bytes);
        op.flush();
        op.close();

    }
}
