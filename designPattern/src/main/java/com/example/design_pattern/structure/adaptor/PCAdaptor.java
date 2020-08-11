package com.example.design_pattern.structure.adaptor;

public class PCAdaptor extends Adaptor{
    public PCAdaptor(ICharger charger) {
        super(charger);
    }
    @Override
    public void supply() {
        System.out.println(charger.charge() + " for pc");
    }
}
