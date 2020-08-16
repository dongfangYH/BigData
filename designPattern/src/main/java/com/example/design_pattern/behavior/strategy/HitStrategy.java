package com.example.design_pattern.behavior.strategy;

public class HitStrategy implements Strategy{
    @Override
    public void executeStrategy() {
        System.out.println("execute strategy HitStrategy|");
    }
}
