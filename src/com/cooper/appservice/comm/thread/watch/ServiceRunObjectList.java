package com.cooper.appservice.comm.thread.watch;

import src.com.cooper.appservice.comm.thread.RunnableObject;
import org.apache.http.annotation.GuardedBy;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by ZZQ on 2017/1/24.
 */
public class    ServiceRunObjectList {
    @GuardedBy("this")
    private static final ConcurrentLinkedQueue<RunnableObject> runnableList = new ConcurrentLinkedQueue<RunnableObject>();

    static void addJob(RunnableObject runnableObject) {
        runnableList.add(runnableObject);
    }
    /**
     *
     * @param runnableObject
     */
    public static void removeObject(RunnableObject runnableObject) {
        runnableList.remove(runnableObject);
    }

    public static ArrayList<RunnableObject> getRunnableList() {
        synchronized (runnableList) {
            return new ArrayList<RunnableObject>(runnableList);
        }
    }
}
