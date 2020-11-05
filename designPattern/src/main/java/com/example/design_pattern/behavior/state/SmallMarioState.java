package com.example.design_pattern.behavior.state;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-05 15:35
 **/
public class SmallMarioState implements MarioState{

    private static final SmallMarioState instance = new SmallMarioState();

    public static SmallMarioState getInstance() {
        return instance;
    }

    private SmallMarioState(){

    }

    @Override
    public long getScore() {
        return 0;
    }
}
