package com.example.sortalgo;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-08-18 15:18
 **/
public class InitClass {

    public static final String DATA = "12345";

    public static int num = 1;

    static {
        System.out.println("static block.");
    }

    public InitClass(){
        System.out.println("call InitClass construct.");
    }
}
