package com.example.gof.behavior.observer.eventbus;

public class Client {

    public static void main(String[] args){
        ObserverA a1 = new ObserverA("observer-a1");
        ObserverA a2 = new ObserverA("observer-a2");
        ObserverB b1 = new ObserverB("observer-b1");
        ObserverB b2 = new ObserverB("observer-b2");
        EventBus eventBus = new EventBus();

        eventBus.register(a1);
        eventBus.register(a2);
        eventBus.register(b1);

        eventBus.post("hahaha");
        Event event = new Event("Boom!");
        eventBus.post(event);

        eventBus.unregister(a1);
        eventBus.unregister(a2);
        eventBus.register(b2);

        eventBus.post("hehe!");
        eventBus.post(event);
    }
}
