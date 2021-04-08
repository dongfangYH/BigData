package com.example.gof.behavior.interpret;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-11 11:06
 **/
public class NumOperateInterpreter implements Interpreter{

    private final NumInterpreter numInterpreter1, numInterpreter2;
    private final OperatorInterpreter operatorInterpreter;

    public NumOperateInterpreter(NumInterpreter numInterpreter1, NumInterpreter numInterpreter2,
                                 OperatorInterpreter operatorInterpreter) {
        this.numInterpreter1 = numInterpreter1;
        this.numInterpreter2 = numInterpreter2;
        this.operatorInterpreter = operatorInterpreter;
    }

    @Override
    public boolean interpret() {
        Long num1 = numInterpreter1.getRealNum();
        Long num2 = numInterpreter2.getRealNum();

        boolean result = false;

        switch (operatorInterpreter.getOperator()){
            case ">":
                result = (num1 > num2);
                break;
            case "<":
                result = (num1 < num2);
                break;
            case "==":
                result = (num1 == num2);
                break;
        }

        return result;
    }

    @Override
    public ExecuteOrder getOrder() {
        return operatorInterpreter.getOrder();
    }

}
