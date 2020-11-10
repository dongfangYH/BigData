package com.example.design_pattern.behavior.visitor;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-10 10:14
 **/
public class WordFile extends ResourceFile{

    public WordFile(String path) {
        super(path);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
