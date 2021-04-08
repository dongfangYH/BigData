package com.example.gof.creator.factory.abs;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-06-17 10:46
 **/
public class WindowsPhotoshop extends Photoshop{
    public WindowsPhotoshop(String name) {
        super(name);
    }

    @Override
    public void draw() {
        System.out.println("use windows photoshop draw...");
    }
}
