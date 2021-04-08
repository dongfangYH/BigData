package com.example.gof.behavior.template;

/**
 * 宝马汽车模型
 **/
public class BMW extends Car{

    @Override
    protected void start() {
        System.out.println("BMW start ...");
    }

    @Override
    protected void stop() {
        System.out.println("BMW stop ...");
    }
}
