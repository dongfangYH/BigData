package com.example.design_pattern.behavior.observer;

public class Follower implements Observer{

    private final String name;

    public Follower(String name) {
        this.name = name;
    }

    @Override
    public void update(String context) {
        System.out.println("Follower " + name + " receive update : " + context);
    }
}
