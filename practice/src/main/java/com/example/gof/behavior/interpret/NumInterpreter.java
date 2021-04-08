package com.example.gof.behavior.interpret;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-11 10:50
 **/
public class NumInterpreter implements Interpreter{

    private final Long realNum;

    public NumInterpreter(String num) {
        this.realNum = Long.valueOf(num);
    }

    public NumInterpreter(Long num){
        this.realNum = num;
    }

    @Override
    public boolean interpret() {
        return true;
    }

    @Override
    public ExecuteOrder getOrder() {
        return ExecuteOrder.EAGER;
    }

    public Long getRealNum() {
        return realNum;
    }
}
