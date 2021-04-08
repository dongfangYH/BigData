package com.example.gof.creator.factory.abs;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-06-17 10:45
 **/
public class LinuxPhotoshop extends Photoshop{
    public LinuxPhotoshop(String name) {
        super(name);
    }

    @Override
    public void draw() {
        System.out.println("use linux photoshop draw...");
    }
}
