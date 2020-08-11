package com.example.design_pattern.behavior.observer.java;

import java.util.Observable;
import java.util.Observer;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-08-11 16:13
 **/
public class Hunter implements Observer {

    private final String name;

    public Hunter(String name) {
        this.name = name;
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println(name + "-> receive msg from : " + o.getClass().getName() + ", arg : " + arg);
    }
}
