package com.example;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-06-10 10:56
 **/
public class Main {
    public static void main(String[] args) throws Exception{
        File file = new File("/home/lmh/test4.txt");
        FileOutputStream out = new FileOutputStream(file);
        String line = "key\001gallery\002launcher\001v3\002\\N\001phone\003mi9\002type\003\\N";
        out.write(line.getBytes("UTF-8"));
        out.flush();
        out.close();
    }
}
