package com.example.design_pattern.behavior.mediator;

import java.util.List;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-11 16:23
 **/
public class InfoDialog extends AbstractComponent{

    protected InfoDialog(String name) {
        super(name, ComponentType.Dialog);
    }

    @Override
    public Event trigger() {
        System.out.println("component : " + getName() + " trigger.");
        return new AbstractEvent(this) {
            @Override
            public List<ComponentType> getInteractComponent() {
                return null;
            }
        };
    }
}
