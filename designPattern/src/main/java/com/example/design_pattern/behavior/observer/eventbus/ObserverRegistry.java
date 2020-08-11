package com.example.design_pattern.behavior.observer.eventbus;

import com.example.design_pattern.behavior.observer.Observer;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class ObserverRegistry {

    /**
     * 注册表
     */
    private final ConcurrentMap<Class<?>, CopyOnWriteArraySet<ObserverAction>> registry =
            new ConcurrentHashMap<>();


    /**
     * 注册观察者
     * @param observer
     */
    public void register(Object observer){
        for (Map.Entry<Class<?>, Collection<ObserverAction>> entry : findAllObserverActions(observer).entrySet()){
            Class<?> eventType = entry.getKey();
            registry.putIfAbsent(eventType, new CopyOnWriteArraySet<>());
            registry.get(eventType).addAll(entry.getValue());
        }
    }

    public void unregister(Object observer){
        Class<?> clazz = observer.getClass();
        List<Method> methods = getAnnotatedMethods(clazz);
        for (Method method : methods){
            Class<?> eventType = method.getParameterTypes()[0];
            CopyOnWriteArraySet<ObserverAction> matchActions = registry.get(eventType);
            if (matchActions != null){
                for (ObserverAction observerAction : matchActions){
                    if (observerAction.getTarget().equals(observer)){
                        matchActions.remove(observerAction);
                    }
                }
            }
        }
    }

    /**
     * 找到匹配事件的观察者
     * @param event
     * @return
     */
    public List<ObserverAction> getMatchedObserverActions(Object event){
        List<ObserverAction> observerActions = new LinkedList<>();
        Class<?> postedEvent = event.getClass();
        for (Map.Entry<Class<?>, CopyOnWriteArraySet<ObserverAction>> entry : registry.entrySet()){
            Class<?> eventType =  entry.getKey();
            if (postedEvent.isAssignableFrom(eventType)){
                observerActions.addAll(entry.getValue());
            }
        }
        return observerActions;
    }

    /**
     * 找到观察者类的所有Subscribe方法
     * @param observer
     * @return
     */
    private Map<Class<?>, Collection<ObserverAction>> findAllObserverActions(Object observer){
        Map<Class<?>, Collection<ObserverAction>> observerActions = new HashMap<>();
        Class<?> clazz = observer.getClass();
        for (Method method : getAnnotatedMethods(clazz)){
            Class<?> eventType = method.getParameterTypes()[0];
            observerActions.putIfAbsent(eventType, new ArrayList<>());
            observerActions.get(eventType).add(new ObserverAction(observer, method));
        }
        return observerActions;
    }

    /**
     * 得到Subscribe 标注的方法
     * @param clazz
     * @return
     */
    private List<Method> getAnnotatedMethods(Class<?> clazz){
        List<Method> methods = new LinkedList<>();
        for (Method method : clazz.getDeclaredMethods()){
            if (method.isAnnotationPresent(Subscribe.class)){
                if (method.getParameterTypes().length != 1)
                    throw new RuntimeException("@Subscribe method must have exactly one param.");
                methods.add(method);
            }
        }
        return methods;
    }
}
