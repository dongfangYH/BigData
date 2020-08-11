package com.example.design_pattern.behavior.observer.java;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-08-11 16:15
 **/
public class Client {

    public static void main(String[] args){
        Rabbit rabbit = new Rabbit();
        Hunter lion = new Hunter("lion");
        Hunter tiger = new Hunter("tiger");
        rabbit.addObserver(lion);
        rabbit.addObserver(tiger);
        rabbit.postEvent("sleep!");
        rabbit.postEvent("wake up!");
    }
}
