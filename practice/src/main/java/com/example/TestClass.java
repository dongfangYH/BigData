package com.example;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-24 10:59
 **/
public class TestClass {

    private Long id;
    private String name;
    private static final AtomicLong atomicLong = new AtomicLong();

    public TestClass(){
    }

    private TestClass(Long id){
        this.id = id;
    }

    public TestClass(String name) {
        this(atomicLong.getAndIncrement());
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
