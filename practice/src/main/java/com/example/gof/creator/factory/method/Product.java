package com.example.gof.creator.factory.method;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-06-15 11:12
 **/
public abstract class Product {

    public Product(String name) {
        this.name = name;
    }

    protected String name;

    public String getName() {
        return name;
    }

    public abstract void printInfo();
}
