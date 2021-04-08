package com.example.gof.creator.factory.method;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-06-15 11:36
 **/
public class Main {
    public static void main(String[] args){
        Factory factory = new ProductFactory();
        ProductA a = factory.produce(ProductA.class, "productA", "aaa");
        ProductB b = factory.produce(ProductB.class, "productB", "bbb");
        a.printInfo();
        b.printInfo();
    }
}
