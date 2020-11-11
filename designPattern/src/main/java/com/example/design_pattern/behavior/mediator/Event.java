package com.example.design_pattern.behavior.mediator;

import java.util.List;

public interface Event {

    Component getSource();

    List<ComponentType> getInteractComponent();

}
