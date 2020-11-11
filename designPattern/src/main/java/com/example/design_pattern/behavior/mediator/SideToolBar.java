package com.example.design_pattern.behavior.mediator;

import java.util.LinkedList;
import java.util.List;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-11 16:45
 **/
public class SideToolBar extends AbstractComponent{

    protected SideToolBar(String name) {
        super(name, ComponentType.ToolBar);
    }

    @Override
    public Event trigger() {
        System.out.println("component : " + getName() + " trigger.");
        return new AbstractEvent(this) {
            @Override
            public List<ComponentType> getInteractComponent() {
                List<ComponentType> types = new LinkedList<>();
                types.add(ComponentType.Dialog);
                types.add(ComponentType.Button);
                return types;
            }
        };
    }
}
