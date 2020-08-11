package com.example.design_pattern.behavior.observer.java;

import java.util.Observable;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-08-11 16:12
 **/
public class Rabbit extends Observable {

    private String event;

    @Override
    protected synchronized void setChanged() {
        super.setChanged();
        notifyObservers(event);
    }

    public void postEvent(String event){
        this.event = event;
        setChanged();
    }
}
