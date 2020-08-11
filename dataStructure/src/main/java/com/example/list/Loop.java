package com.example.list;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-04-24 15:09
 **/
public class Loop {

    public static final Integer stepIn(Integer n){
        if (n == 1) return 1;
        if (n == 2) return 2;
        return stepIn(n - 1) + stepIn(n - 2);
    }

    public static void main(String[] args){
        System.out.println(stepIn(4));
    }
}
