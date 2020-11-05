package com.example.design_pattern.behavior.state;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-05 15:34
 **/
public class SuperMarioState implements MarioState{

    private static final SuperMarioState instance = new SuperMarioState();

    private SuperMarioState() {
    }

    public static SuperMarioState getInstance() {
        return instance;
    }

    @Override
    public long getScore() {
        return 50;
    }
}
