package com.example.design_pattern.creator.factory.method;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-06-15 11:13
 **/
public class ProductA extends Product {

    private String attr;

    public ProductA(String name) {
        super(name);
    }

    public ProductA(String name, String attr) {
        super(name);
        this.attr = attr;
    }

    public void printInfo() {
        System.out.println(getName() + "'s attr : " + getAttr());
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }
}
