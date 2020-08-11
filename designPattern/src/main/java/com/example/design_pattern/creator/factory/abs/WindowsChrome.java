package com.example.design_pattern.creator.factory.abs;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-06-17 10:44
 **/
public class WindowsChrome extends Chrome{
    public WindowsChrome(String name) {
        super(name);
    }

    @Override
    public void enjoy() {
        System.out.println("enjoy windows chrome...");
    }
}
