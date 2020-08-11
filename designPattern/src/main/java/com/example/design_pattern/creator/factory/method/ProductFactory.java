package com.example.design_pattern.creator.factory.method;

import java.lang.reflect.Constructor;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-06-15 11:19
 **/
public class ProductFactory implements Factory {

    public <T extends Product> T produce(Class<T> clazz, String... args) {
        Class[] type = new Class[args.length];

        for (int i= 0; i <type.length; i++){
            type[i] = String.class;
        }

        try{
            Constructor<T> constructor = clazz.getDeclaredConstructor(type);
            return constructor.newInstance(args);
        }catch (Exception e){

        }
        return null;
    }

}
