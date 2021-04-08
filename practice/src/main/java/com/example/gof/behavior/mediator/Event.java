package com.example.gof.behavior.mediator;

import java.util.List;

public interface Event {

    Component getSource();

    List<ComponentType> getInteractComponent();

}
