package com.cooper.appservice.comm.thread.task;

/**
 * Created by ZZQ on 2017/2/5.
 */
public interface UncaughtExceptionHandler {
    public void uncaughtException(Throwable t, ServiceRunInterface task);
}
