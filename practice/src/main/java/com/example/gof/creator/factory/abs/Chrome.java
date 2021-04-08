package com.example.gof.creator.factory.abs;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-06-15 11:47
 **/
public abstract class Chrome {

    private String name;

    public String getName() {
        return name;
    }

    public Chrome(String name) {
        this.name = name;
    }

    public abstract void enjoy();
}
