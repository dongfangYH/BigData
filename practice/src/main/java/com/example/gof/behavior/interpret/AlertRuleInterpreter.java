package com.example.gof.behavior.interpret;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-11 10:37
 **/
public class AlertRuleInterpreter {

    private Deque<String> numStack = new LinkedList<>();
    private Deque<String> operatorStack = new LinkedList<>();
    private Deque<Boolean> executeQueue = new LinkedList<>();
    private Deque<OperatorInterpreter> delayOpQueue = new LinkedList<>();

    // key1 > 100 && key2 < 1000 || key3 == 200
    public AlertRuleInterpreter(String expression){
        String element[] = expression.split(" ");
        for (int i = 0; i < element.length; i++){
            if (i % 2 == 0){
                numStack.add(element[i]);
            }else {
                operatorStack.add(element[i]);
            }
        }
    }

    // <String, Long> apiStat = new HashMap<>();
    // apiStat.put("key1", 103);
    // apiStat.put("key2", 987);
    public boolean interpret(Map<String, Long> stats){

        while (!numStack.isEmpty() || !operatorStack.isEmpty()){

            String operator = operatorStack.pollFirst();
            OperatorInterpreter operatorInterpreter = new OperatorInterpreter(operator);

            if (operatorInterpreter.getOrder() == ExecuteOrder.DELAY){
                delayOpQueue.add(operatorInterpreter);
            }else {
                String num1 = numStack.pollFirst();
                String num2 = numStack.pollFirst();

                if (null == num1 || null == num2){
                    throw new IllegalArgumentException("IllegalExpression.");
                }

                Long realNum1 = stats.get(num1);
                Long realNum2 = stats.get(num2);
                NumInterpreter numInterpreter1 = getNumInterpreter(realNum1, num1);
                NumInterpreter numInterpreter2 = getNumInterpreter(realNum2, num2);
                NumOperateInterpreter numOperateInterpreter = new NumOperateInterpreter(numInterpreter1, numInterpreter2, operatorInterpreter);
                executeQueue.add(numOperateInterpreter.interpret());
            }

        }

        while (!executeQueue.isEmpty() || !delayOpQueue.isEmpty()){

            if (executeQueue.size() == 1){
                return executeQueue.poll();
            }

            Boolean rs1 = executeQueue.pollFirst();
            Boolean rs2 = executeQueue.pollFirst();
            OperatorInterpreter ops = delayOpQueue.pollFirst();

            if (rs1 == null || rs2 == null || ops == null){
                throw new IllegalArgumentException("IllegalExpression.");
            }

            LogicOperateInterpreter logicOpt = new LogicOperateInterpreter(rs1, rs2, ops);

            executeQueue.addFirst(logicOpt.interpret());
        }

        return false;
    }

    private NumInterpreter getNumInterpreter(Long num, String num1) {
        if (num != null){
            return new NumInterpreter(num);
        }else {
            return new NumInterpreter(num1);
        }
    }


}
