package com.example.gof.behavior.observer.eventbus;

public class ObserverA {

    private final String name;

    public ObserverA(String name) {
        this.name = name;
    }

    @Subscribe
    public void trigger(String msg){
        System.out.println(name + " receive msg : " + msg);
    }
}
