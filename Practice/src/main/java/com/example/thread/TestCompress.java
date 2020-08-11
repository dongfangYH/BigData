package com.example.thread;

import java.util.LinkedList;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-05-20 10:09
 **/
public class TestCompress {

    public static void main(String[] args){
        String source = "abbbbccdefff";
        String result = "";
        LinkedList<String> stack = new LinkedList<>();
        for (int i = 0; i < source.length(); i++){
            String e = String.valueOf(source.charAt(i));

            if (stack.isEmpty()){
                stack.push(e);
            }else {
                String last = stack.peek();
                if (last.equals(e)){
                    stack.push(e);
                }else {
                    result += (e + (stack.size() > 1 ? stack.size() : ""));
                    stack.clear();
                }
            }
        }
        System.out.println(result);
    }
}
