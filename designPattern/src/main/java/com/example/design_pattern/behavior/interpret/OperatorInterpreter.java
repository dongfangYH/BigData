package com.example.design_pattern.behavior.interpret;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-11 11:00
 **/
public class OperatorInterpreter implements Interpreter{

    private final String operator;

    public OperatorInterpreter(String operator) {
        if (!("||".equals(operator) || ">".equals(operator) || "<".equals(operator) ||
                "&&".equals(operator) || "==".equals(operator))){
            throw new IllegalArgumentException("illegal operator : " + operator);
        }
        this.operator = operator;
    }

    @Override
    public boolean interpret() {
        return true;
    }

    @Override
    public ExecuteOrder getOrder() {
        return (operator.equals(">") || operator.equals("<") || operator.equals("==")) ? ExecuteOrder.EAGER : ExecuteOrder.DELAY;
    }

    public String getOperator() {
        return operator;
    }
}
