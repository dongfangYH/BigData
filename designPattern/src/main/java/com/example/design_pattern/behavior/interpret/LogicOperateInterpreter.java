package com.example.design_pattern.behavior.interpret;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-11 14:10
 **/
public class LogicOperateInterpreter implements Interpreter{

    private final Boolean rs1, rs2;
    private final OperatorInterpreter operatorInterpreter;

    public LogicOperateInterpreter(Boolean rs1, Boolean rs2, OperatorInterpreter operatorInterpreter) {
        this.rs1 = rs1;
        this.rs2 = rs2;
        this.operatorInterpreter = operatorInterpreter;
    }

    @Override
    public boolean interpret() {
        String operator = operatorInterpreter.getOperator();
        boolean result = false;
        switch (operator){
            case "&&":
                result = (rs1 && rs2);
                break;
            case "||":
                result = (rs1 || rs2);
                break;
        }

        return result;
    }

    @Override
    public ExecuteOrder getOrder() {
        return operatorInterpreter.getOrder();
    }
}
