package com.example.design_pattern.lsp;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-06-09 16:56
 **/
public class Main {


    public static void main(String[] args){
        Map<String, String> map = new HashMap<String, String>();
        HashMap<String, String> map1 = new HashMap<String, String>();
        map1.put("b", "a");
        map.put("a", "b");
        Son son = new Son();
        son.doSomething(map);
        son.doSomething(map1);

    }
}
