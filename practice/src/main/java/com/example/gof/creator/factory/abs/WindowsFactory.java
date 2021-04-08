package com.example.gof.creator.factory.abs;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-06-17 10:48
 **/
public class WindowsFactory extends AbstractFactory{
    @Override
    public <T extends Chrome> Chrome productChrome(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor(String.class).newInstance(clazz.getSimpleName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T extends Photoshop> Photoshop productPhotoshop(Class<T> tClass) {
        try {
            return tClass.getDeclaredConstructor(String.class).newInstance(tClass.getSimpleName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
