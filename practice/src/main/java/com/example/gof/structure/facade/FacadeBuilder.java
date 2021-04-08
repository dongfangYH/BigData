package com.example.gof.structure.facade;

public class FacadeBuilder {

    private IComponentA componentA;
    private IComponentB componentB;
    private IComponentC componentC;

    public FacadeBuilder withComponentA(IComponentA componentA){
        this.componentA = componentA;
        return this;
    }

    public FacadeBuilder withComponentB(IComponentB componentB){
        this.componentB = componentB;
        return this;
    }

    public FacadeBuilder withComponentC(IComponentC componentC){
        this.componentC = componentC;
        return this;
    }

    public IFacade build(){
        if (componentA == null){
            throw new IllegalArgumentException("require componentA.");
        }

        if (componentB == null){
            throw new IllegalArgumentException("require componentB.");
        }

        if (componentC == null){
            throw new IllegalArgumentException("require componentC.");
        }

        return new Facade(componentA, componentB, componentC);
    }


}
