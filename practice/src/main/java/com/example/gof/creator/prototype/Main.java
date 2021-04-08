package com.example.gof.creator.prototype;

import com.example.design_pattern.creator.builder.K30Pro;
import com.example.design_pattern.creator.builder.K40Pro;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-07-01 15:57
 **/
public class Main {

    public static void main(String[] args) throws Exception {
        Prototype p1 = new Prototype();
        p1.setId(1);
        p1.setId2(2);
        p1.setName("p1");
        p1.setClazz(K40Pro.class);
        List<String> carries = new ArrayList<>();
        carries.add("13kg");
        carries.add("10kg");
        p1.addCarries(carries);

        Prototype p2 = p1.clone();
        p2.setId(3);
        p2.setId2(30);
        p2.setName("p2");
        List<String> carries2 = new ArrayList<>();
        carries2.add("3kg");
        carries2.add("1kg");
        p2.addCarries(carries2);
        p2.setClazz(K30Pro.class);

        System.out.println(p1.getId());
        System.out.println(p1.getId2());
        System.out.println(p1.getName());
        System.out.println(p1.getCarries());
        System.out.println(p1.getClazz());

        System.out.println(p2.getId());
        System.out.println(p2.getId2());
        System.out.println(p2.getName());
        System.out.println(p2.getCarries());
        System.out.println(p2.getClazz());
    }
}
