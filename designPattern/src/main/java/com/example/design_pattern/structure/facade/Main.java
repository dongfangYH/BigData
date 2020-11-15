package com.example.design_pattern.structure.facade;

public class Main {
    public static void main(String[] args) {

        IFacade facade = new FacadeBuilder()
                .withComponentA(new ComponentA())
                .withComponentB(new ComponentB())
                .withComponentC(new ComponentC())
                .build();

        facade.doSomething();
    }
}
