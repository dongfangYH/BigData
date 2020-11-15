package com.example.design_pattern.behavior.template;

/**
 * 定义一个汽车模板
 **/
public abstract class Car {

    /**
     * 定义基本方法，启动汽车
     */
    protected abstract void start();

    /**
     * 定义基本方法，停止汽车
     */
    protected abstract void stop();

    /**
     * 模板方法，驾驶汽车，包含启动汽车和停止汽车过程
     */
    public void drive(){
        this.start();
        this.stop();
    }
}
