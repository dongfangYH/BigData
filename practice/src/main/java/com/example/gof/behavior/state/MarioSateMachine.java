package com.example.gof.behavior.state;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-05 15:38
 **/
public class MarioSateMachine extends StateMachine{

    public MarioSateMachine(MarioState marioState) {
        super(marioState);
    }

    @Override
    public void eatMarshRoom() {
        stateTransfer(SuperMarioState.getInstance());
    }

    @Override
    public void eatChocolate() {
        stateTransfer(FireMarioState.getInstance());
    }
}
