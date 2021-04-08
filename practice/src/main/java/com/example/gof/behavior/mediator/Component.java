package com.example.gof.behavior.mediator;

public interface Component {

    String getName();

    ComponentType getType();

    Event trigger();
}
