package com.cooper.appservice.comm.thread.future;

import src.com.cooper.appservice.comm.thread.CallableObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-12-26
 * Time: ÏÂÎç4:42
 * To change this template use File | Settings | File Templates.
 */
public class ServiceFuture<E> implements Future<E> {
    private final CallableObject<E> callableObject;
    private boolean cancelDone = false;
    private Boolean cancelRemove = null;

    public ServiceFuture(CallableObject<E> callableObject) {
        this.callableObject = callableObject;
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        cancelDone = true;
        boolean b = this.callableObject.cancelTask(mayInterruptIfRunning);
        if(cancelRemove == null) {
            cancelRemove = b;
        }
        return b;
    }

    public boolean isCancelled() {
        if(cancelRemove == null) {
            return false;
        }
        return cancelRemove;
    }

    public boolean isDone() {
        if(cancelDone) return true;
        return callableObject.isGetResultFlg();  //To change body of implemented methods use File | Settings | File Templates.
    }

    public E get() throws InterruptedException, ExecutionException {
        return get(0);
    }

    public E get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        if(unit == null) {
            unit = TimeUnit.SECONDS;
        }
        long time = unit.toMillis(timeout);
        return get(time);
    }

    private E get(long timeout) throws InterruptedException {
        synchronized (callableObject) {
            if(!callableObject.isGetResultFlg()) {
                callableObject.wait(timeout);
            }
        }
        return callableObject.getResult();
    }
}
