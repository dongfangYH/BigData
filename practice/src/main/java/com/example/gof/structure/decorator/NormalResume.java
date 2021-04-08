package com.example.gof.structure.decorator;

/**
 * 普通简历
 **/
public class NormalResume extends Resume{
    @Override
    public void info() {
        //输出一般简历信息
        System.out.println("normal resume info.");
    }
}
