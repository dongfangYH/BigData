package com.example.gof.behavior.mediator;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-11 16:32
 **/
public abstract class AbstractEvent implements Event{

    private final Component source;

    protected AbstractEvent(Component component) {
        this.source = component;
    }

    @Override
    public Component getSource() {
        return source;
    }
}
