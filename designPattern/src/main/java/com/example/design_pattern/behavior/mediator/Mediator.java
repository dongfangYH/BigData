package com.example.design_pattern.behavior.mediator;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-11 16:06
 **/
public abstract class Mediator {

    abstract void register(Component component);

    abstract void handle(Event event);
}
