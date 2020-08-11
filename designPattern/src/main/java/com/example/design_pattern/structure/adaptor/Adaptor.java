package com.example.design_pattern.structure.adaptor;

public abstract class Adaptor {
    ICharger charger;
    public Adaptor(ICharger charger) {
        this.charger = charger;
    }
    public abstract void supply();
}
