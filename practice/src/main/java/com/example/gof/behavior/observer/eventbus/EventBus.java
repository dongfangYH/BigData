package com.example.gof.behavior.observer.eventbus;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EventBus {

    private Executor executor;

    private ObserverRegistry registry = new ObserverRegistry();

    public EventBus(Executor executor) {
        this.executor = executor;
    }

    public EventBus() {
        this(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2));
    }

    public void register(Object observer){
        registry.register(observer);
    }

    public void unregister(Object observer){
        registry.unregister(observer);
    }

    public void post(Object event){
        for (ObserverAction observerAction : registry.getMatchedObserverActions(event)){
            executor.execute(()-> observerAction.execute(event));
        }
    }
}
