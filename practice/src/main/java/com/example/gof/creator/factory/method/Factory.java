package com.example.gof.creator.factory.method;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-06-15 11:17
 **/
public interface Factory {

    default <T extends Product> T produce(Class<T> clazz){
        return produce(clazz, null);
    }

    <T extends Product> T produce(Class<T> clazz, String... args);
}
