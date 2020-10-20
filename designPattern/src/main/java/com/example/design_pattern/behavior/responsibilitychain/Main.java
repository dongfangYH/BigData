package com.example.design_pattern.behavior.responsibilitychain;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-10-12 16:51
 **/
public class Main {

    public static void main(String[] args){
        SensitiveContentFilterChain chain = new SensitiveContentFilterChain();
        chain.addHandler(new SexyWordHandler());
        chain.addHandler(new ViolentWordHandler());
        System.out.println(chain.handle("show your nipple, or i will kill you."));
    }
}
