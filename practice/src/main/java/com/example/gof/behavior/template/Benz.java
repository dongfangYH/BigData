package com.example.gof.behavior.template;

/**
 * 奔驰汽车模型
 **/
public class Benz extends Car{

    @Override
    protected void start() {
        System.out.println("benz start ...");
    }

    @Override
    protected void stop() {
        System.out.println("benz stop ...");
    }
}
