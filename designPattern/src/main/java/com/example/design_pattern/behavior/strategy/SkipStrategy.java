package com.example.design_pattern.behavior.strategy;

public class SkipStrategy implements Strategy{
    @Override
    public void executeStrategy() {
        System.out.println("execute strategy SkipStrategy|");
    }
}
