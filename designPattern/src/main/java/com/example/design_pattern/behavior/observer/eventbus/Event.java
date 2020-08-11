package com.example.design_pattern.behavior.observer.eventbus;

public class Event {

    private final String name;
    public Event(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
