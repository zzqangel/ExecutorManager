package com.cooper.appservice.comm.thread.queue;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-12-20
 * Time: ÏÂÎç1:19
 * To change this template use File | Settings | File Templates.
 */
public class ServiceLockQueue<E> extends ConcurrentLinkedQueue<E> {
    private boolean lock = false;

    public ServiceLockQueue() {
        super();
    }

    public synchronized void lockQueue() {
        this.lock = true;
    }

    public boolean add(E e) {
        if(lock) return false;
        return super.add(e);
    }

    public synchronized void unLockQueue() {
        this.lock = false;
    }

    public boolean isLock() {
        return lock;
    }
}
