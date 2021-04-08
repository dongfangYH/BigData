package com.example.gof.behavior.observer;

/**
 * 被观察者
 */
public interface Observable {

    /**
     * 添加观察者
     * @param observer
     */
    void addObserver(Observer observer);

    /**
     * 移除观察者
     * @param observer
     */
    void removeObserver(Observer observer);

    /**
     * 事件更新通知所有观察者
     * @param context
     */
    void notifyObserver(String context);
}
