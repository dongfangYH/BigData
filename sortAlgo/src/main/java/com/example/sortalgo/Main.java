package com.example.sortalgo;

public class Main {

    public static void main(String[] args)throws Exception{
        Class<InitClass> clazz = (Class<InitClass>) Class.forName("com.example.sortalgo.InitClass", false, Main.class.getClassLoader());
        System.out.println();
    }
}