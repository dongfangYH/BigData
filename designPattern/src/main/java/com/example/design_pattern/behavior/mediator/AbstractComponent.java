package com.example.design_pattern.behavior.mediator;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-11 16:11
 **/
public abstract class AbstractComponent implements Component{

    private final String name;
    private final ComponentType componentType;

    protected AbstractComponent(String name, ComponentType componentType) {
        this.name = name;
        this.componentType = componentType;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ComponentType getType() {
        return componentType;
    }

}
