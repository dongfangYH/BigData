package com.example.design_pattern.behavior.mediator;

import java.util.LinkedList;
import java.util.List;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-11 16:18
 **/
public class ConfirmButton extends AbstractComponent{

    protected ConfirmButton(String name) {
        super(name, ComponentType.Button);
    }

    @Override
    public Event trigger() {
        System.out.println("component : " + getName() + " trigger.");
        return new AbstractEvent(this) {
            @Override
            public List<ComponentType> getInteractComponent() {
                List<ComponentType> types = new LinkedList<>();
                types.add(ComponentType.Dialog);
                return types;
            }
        };
    }


}