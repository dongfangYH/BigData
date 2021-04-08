package com.example.gof.creator.factory.method;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-06-15 11:15
 **/
public class ProductB extends Product {

    private String carr;

    public ProductB(String name) {
        super(name);
    }

    public ProductB(String name, String carr) {
        super(name);
        this.carr = carr;
    }

    public void printInfo() {
        System.out.println(getName() + "'s carr : " + getCarr());
    }

    public String getCarr() {
        return carr;
    }

    public void setCarr(String carr) {
        this.carr = carr;
    }
}
