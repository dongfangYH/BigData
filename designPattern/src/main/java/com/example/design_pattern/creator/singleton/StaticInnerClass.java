package com.example.design_pattern.creator.singleton;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-05-20 15:41
 **/
public class StaticInnerClass {

    private StaticInnerClass(){}

    public StaticInnerClass getInstance(){
        return Instance.instance;
    }

    private static final class Instance{
        static StaticInnerClass instance = new StaticInnerClass();
    }
}
