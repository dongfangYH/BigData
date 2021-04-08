package com.example.gof.creator.builder;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-06-23 09:43
 **/
public class Director {

    private XiaoMiBuilder xiaoMiBuilder = new XiaoMiBuilder();
    private RedMiBuilder redMiBuilder = new RedMiBuilder();

    public XiaoMi buildMi10Pro(){
        xiaoMiBuilder.setaClass(Mi10Pro.class);
        return xiaoMiBuilder.build();
    }

    public RedMi buildK30Pro(){
        redMiBuilder.setaClass(K30Pro.class);
        return redMiBuilder.build();
    }

    public XiaoMi buildMix4(){
        xiaoMiBuilder.setaClass(Mix4.class);
        return xiaoMiBuilder.build();
    }

    public RedMi buildK40Pro(){
        redMiBuilder.setaClass(K40Pro.class);
        return redMiBuilder.build();
    }

}
