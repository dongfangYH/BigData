package com.example.gof.behavior.iterator;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-10 08:48
 **/
public class Main {

    public static void main(String[] args){
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        Iterator<String> it = list.iterator();
        while (it.hasNext()){
            System.out.println(it.next());
        }
    }
}
