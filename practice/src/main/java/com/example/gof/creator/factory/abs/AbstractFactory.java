package com.example.gof.creator.factory.abs;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-06-15 11:54
 **/
public abstract class AbstractFactory {

    public abstract <T extends Chrome> Chrome productChrome(Class<T> clazz);

    public abstract <T extends Photoshop> Photoshop productPhotoshop(Class<T> tClass);
}
