package com.example.design_pattern.behavior.observer.eventbus;

public class ObserverB {

    private final String name;

    public ObserverB(String name) {
        this.name = name;
    }

    @Subscribe
    public void trigger(Event event){
        System.out.println(name + " receive event name " + event.getName());
    }
}
