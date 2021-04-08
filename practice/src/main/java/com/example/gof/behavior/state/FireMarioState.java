package com.example.gof.behavior.state;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-05 15:26
 **/
public class FireMarioState implements MarioState{

    private static final FireMarioState instance = new FireMarioState();

    private FireMarioState(){}

    public static FireMarioState getInstance() {
        return instance;
    }

    @Override
    public long getScore() {
        return 100l;
    }
}
