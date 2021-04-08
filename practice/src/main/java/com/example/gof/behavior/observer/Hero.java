package com.example.gof.behavior.observer;

import java.util.LinkedList;
import java.util.List;

public class Hero implements Observable{

    private List<Observer> queue = new LinkedList<>();
    private String context;

    public Hero(String context) {
        this.context = context;
    }

    @Override
    public void addObserver(Observer observer) {
        queue.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        queue.remove(observer);
    }

    @Override
    public void notifyObserver(String context) {
        this.context = context;
        for (Observer observer : queue){
            observer.update(context);
        }
    }
}
