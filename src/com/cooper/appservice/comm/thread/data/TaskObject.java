package com.cooper.appservice.comm.thread.data;

import src.com.cooper.appservice.comm.thread.RunnableObject;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-12-27
 * Time: обнГ1:35
 * To change this template use File | Settings | File Templates.
 */
public abstract class TaskObject {
    public abstract boolean removeTask(RunnableObject runnableObject);

    public abstract void addJob(RunnableObject runnableObject);
}
