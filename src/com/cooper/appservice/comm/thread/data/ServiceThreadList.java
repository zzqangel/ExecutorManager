package com.cooper.appservice.comm.thread.data;

import src.com.cooper.appservice.comm.thread.ServiceThread;
import org.apache.http.annotation.GuardedBy;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Created by ZZQ on 2017/1/24.
 */
public class ServiceThreadList implements Iterable<ServiceThread> {
    @GuardedBy("this")
    private final ConcurrentSkipListSet<ServiceThread> threadList = new ConcurrentSkipListSet<ServiceThread>();

    public ServiceThreadList() {
    }

    public void remove(ServiceThread serviceThread) {
        this.threadList.remove(serviceThread);
    }

    @Override
    public Iterator<ServiceThread> iterator() {
        return threadList.iterator();
    }

    public void removeAll(Collection<ServiceThread> collection) {
        threadList.removeAll(collection);
    }

    public int size() {
        return this.threadList.size();
    }

    public void add(ServiceThread serviceThread) {
        this.threadList.add(serviceThread);
    }

    public void removeThread(ServiceThread serviceThread) {
        this.threadList.remove(serviceThread);
    }
}
