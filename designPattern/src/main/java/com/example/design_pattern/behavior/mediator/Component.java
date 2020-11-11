package com.example.design_pattern.behavior.mediator;

public interface Component {

    String getName();

    ComponentType getType();

    Event trigger();
}
