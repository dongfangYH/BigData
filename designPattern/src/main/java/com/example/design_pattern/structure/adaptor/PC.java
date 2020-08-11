package com.example.design_pattern.structure.adaptor;

public class PC {
    public static void main(String[] args){
        ICharger charger = new PhoneCharger();
        Adaptor adaptor = new PCAdaptor(charger);
        adaptor.supply();
    }
}
