package com.cooper.appservice.comm.thread.data;

import src.com.cooper.appservice.comm.thread.RunnableObject;
import com.cooper.appservice.comm.thread.queue.ServiceLockQueue;

/**
 * Created by ZZQ on 2017/1/24.
 */
public class RunnableObjectList {

    private boolean queueFlg;
    private final ServiceLockQueue<RunnableObject> serviceRunnableList = new ServiceLockQueue<RunnableObject>();

    public void add(RunnableObject runnableObject) {
        this.serviceRunnableList.add(runnableObject);
    }

    public boolean remove(RunnableObject runnableObject) {
        return this.serviceRunnableList.remove(runnableObject);
    }

    public boolean isLock() {
        return this.serviceRunnableList.isLock();
    }

    public void lock() {
        this.serviceRunnableList.lockQueue();
    }

    public void unLock() {
        this.serviceRunnableList.unLockQueue();
    }

    public RunnableObject poll() {
        synchronized (serviceRunnableList) {
            return this.serviceRunnableList.poll();
        }
    }
    public void setQueueFlg(boolean queueFlg) {
        this.queueFlg = queueFlg;
    }

    public RunnableObject getNextQueueJob() {
        if(!queueFlg) {
            serviceRunnableList.lockQueue();
        }
        return serviceRunnableList.poll();
    }
}
