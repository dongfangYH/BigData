package com.example.gof.behavior.state;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-05 15:33
 **/
public class Main {
    public static void main(String[] args){
        StateMachine stateMachine = new MarioSateMachine(SmallMarioState.getInstance());
        stateMachine.eatMarshRoom();
        stateMachine.eatChocolate();
        System.out.println(stateMachine.getScore());
    }
}
