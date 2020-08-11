package com.example.design_pattern.creator.factory.abs;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-06-15 16:54
 **/
public class Main {

    public static void main(String[] args){
        AbstractFactory linuxFactory = new LinuxSoftFactory();
        AbstractFactory windowsFactory = new WindowsFactory();
        Chrome linuxChrome = linuxFactory.productChrome(LinuxChrome.class);
        Photoshop linuxPs = linuxFactory.productPhotoshop(LinuxPhotoshop.class);
        Chrome windowsChrome = windowsFactory.productChrome(WindowsChrome.class);
        Photoshop windowsPs = windowsFactory.productPhotoshop(WindowsPhotoshop.class);
        linuxChrome.enjoy();
        linuxPs.draw();
        windowsChrome.enjoy();
        windowsPs.draw();
    }
}
