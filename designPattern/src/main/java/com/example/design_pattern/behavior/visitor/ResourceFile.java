package com.example.design_pattern.behavior.visitor;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-10 10:10
 **/
public abstract class ResourceFile {

    private String path;

    public ResourceFile(String path) {
        this.path = path;
    }

    public abstract void accept(Visitor visitor);
}
