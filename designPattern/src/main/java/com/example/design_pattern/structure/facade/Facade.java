package com.example.design_pattern.structure.facade;

public class Facade implements IFacade{

    private final IComponentA componentA;
    private final IComponentB componentB;
    private final IComponentC componentC;

    public Facade(IComponentA componentA, IComponentB componentB, IComponentC componentC) {
        this.componentA = componentA;
        this.componentB = componentB;
        this.componentC = componentC;
    }

    @Override
    public void doSomething() {
        componentA.useComponentA();
        componentB.useComponentB();
        componentC.useComponentC();
    }
}
