package com.example.design_pattern.structure.adaptor;

public class PhoneCharger implements ICharger{
    @Override
    public String charge() {
        return "supply 5V 1A power";
    }
}
