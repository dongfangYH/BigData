package com.example.gof.creator.factory.abs;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-06-17 10:43
 **/
public class LinuxChrome extends Chrome{
    public LinuxChrome(String name) {
        super(name);
    }

    @Override
    public void enjoy() {
        System.out.println("enjoy linux chrome...");
    }
}
