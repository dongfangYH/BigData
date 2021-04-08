package com.example.gof.behavior.strategy;

public class SkipStrategy implements Strategy{
    @Override
    public void executeStrategy() {
        System.out.println("execute strategy SkipStrategy|");
    }
}
