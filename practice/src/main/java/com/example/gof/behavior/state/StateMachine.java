package com.example.gof.behavior.state;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-05 15:26
 **/
public abstract class StateMachine {

    private MarioState marioState;

    private long score = 0;

    public StateMachine(MarioState marioState) {
        this.marioState = marioState;
    }

    public void stateTransfer(MarioState state){
        this.marioState = state;
        score += state.getScore();
    }

    public abstract void eatMarshRoom();

    public abstract void eatChocolate();

    public long getScore() {
        return score;
    }

    public MarioState getMarioState() {
        return marioState;
    }
}
