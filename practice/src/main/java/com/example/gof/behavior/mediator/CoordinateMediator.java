package com.example.gof.behavior.mediator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-11 16:10
 **/
public class CoordinateMediator extends Mediator{

    Map<String, Component> map = new HashMap<>();
    Map<ComponentType, List<Component>> componentTypeMap = new HashMap<>();

    @Override
    void register(Component component) {
        if (map.containsKey(component.getName())){
            throw new RuntimeException("component name : " + component.getName() + " already existed.");
        }
        map.put(component.getName(), component);

        if (componentTypeMap.containsKey(component.getType())){
            componentTypeMap.get(component.getType()).add(component);
        }else {
            List<Component> componentList = new LinkedList<>();
            componentList.add(component);
            componentTypeMap.put(component.getType(), componentList);
        }
    }

    @Override
    void handle(Event event) {

        Component source = event.getSource();
        List<ComponentType> types = event.getInteractComponent();

        if (null != types){
            List<Component> interactComponents = new LinkedList<>();

            for (ComponentType componentType : types){
                interactComponents.addAll(
                        componentTypeMap.get(componentType).stream()
                                .filter(c -> !c.getName().equals(source.getName()))
                                .collect(Collectors.toList())
                );
            }

            interactComponents.forEach(c -> {
                String sourceName = source.getName();
                ComponentType sourceType = source.getType();

                String interactComponentName = c.getName();
                ComponentType interactComponentType = c.getType();

                System.out.println("[Component : " + sourceName + ", Type : " + sourceType + "] trigger -> " +
                        " [Component : " + interactComponentName + ", Type : " + interactComponentType + "]");
            });
        }


    }
}
