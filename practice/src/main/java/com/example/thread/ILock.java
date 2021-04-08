package com.example.thread;

/**
 * a simple lock interface
 */
public interface ILock {

    /**
     * return immediately if the current thread can get permit, otherwise it will be block until
     * it can get permit.
     */
    void lock();

    /**
     * try to get lock once. this is a one shot method.
     * @return
     *   false : if the caller thread can get lock
     *   true : current thread successfully get lock
     */
    boolean tryLock();

    /**
     * call this method will release lock if the current thread was a lock holder. otherwise nothing happens.
     */
    void unlock();
}
