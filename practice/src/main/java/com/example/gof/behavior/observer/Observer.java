package com.example.gof.behavior.observer;

/**
 * 观察者
 */
public interface Observer {

    /**
     * 被观察者事件改变通知观察者
     * Q：当观察者数量过多或某个观察者执行较慢则会阻塞被观察者的响应
     * 可以在观察者这里实现异步响应，将作业提交到线程池。
     * 当然不另起线程执行则是同步响应
     * @param context
     */
    void update(String context);
}
