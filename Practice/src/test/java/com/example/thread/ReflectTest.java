package com.example.thread;

import com.example.TestClass;
import junit.framework.TestCase;

import java.lang.reflect.Constructor;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-24 10:54
 **/
public class ReflectTest extends TestCase {

    public void testConstructor(){
        Constructor[] constructors = TestClass.class.getDeclaredConstructors();

        for (Constructor constructor : constructors){
            System.out.println(constructor.getParameterCount());
        }
    }
}
