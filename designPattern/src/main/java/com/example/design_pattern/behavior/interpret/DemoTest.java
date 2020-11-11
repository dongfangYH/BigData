package com.example.design_pattern.behavior.interpret;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-11 10:39
 **/
public class DemoTest {
    public static void main(String[] args){
        String rule = "key1 > 100 && key2 < 30 || key3 < 100 || key4 == 88";
        AlertRuleInterpreter interpreter = new AlertRuleInterpreter(rule);
        Map<String, Long> stats = new HashMap<>();
        stats.put("key1", 101l);
        stats.put("key3", 121l);
        stats.put("key4", 88l);
        stats.put("key2", 12l);
        boolean alert = interpreter.interpret(stats);
        System.out.println(alert);
    }
}
