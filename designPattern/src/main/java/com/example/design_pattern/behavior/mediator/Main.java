package com.example.design_pattern.behavior.mediator;

/**
 * @author yuanhang.liu@tcl.com
 * @description Mediator pattern
 *        several components need to interact with each other.
 *        if these components interact with each other directly, it might cause complex relationship as component grows.
 *        if we use a mediator to coordinate these component, it would become much more easier.
 * @date 2020-11-11 16:52
 **/
public class Main {

    public static void main(String[] args){

        Component button = new ConfirmButton("button");
        Component toolBar = new SideToolBar("tool bar");
        Component dialog = new InfoDialog("dialog");

        Mediator mediator = new CoordinateMediator();
        mediator.register(button);
        mediator.register(toolBar);
        mediator.register(dialog);

        mediator.handle(button.trigger());
        mediator.handle(toolBar.trigger());
        mediator.handle(dialog.trigger());

    }
}
