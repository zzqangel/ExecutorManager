package com.cooper.appservice.comm.thread.data.manager;

import com.cooper.appservice.comm.thread.data.ClassThreadListObject;
import src.com.cooper.appservice.comm.thread.RunnableObject;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-12-21
 * Time: ÏÂÎç2:21
 * To change this template use File | Settings | File Templates.
 */
public class ClassTaskThreadManager {



    private final static ConcurrentHashMap<String, ClassThreadListObject> taskThreadMap = new ConcurrentHashMap<String, ClassThreadListObject>();

    private static final int limit = 5;

    public static void addJob(RunnableObject runnableObject, boolean singleFlg, boolean queueFlg) {
        String taskName = runnableObject.getTaskName();
        ClassThreadListObject list = taskThreadMap.get(taskName);
        if(list == null) {
            list = initThreadList(taskName, singleFlg, queueFlg);
        } else {
            list.setQueueFlg(queueFlg);
        }
        list.addJob(runnableObject);
    }

    private static ClassThreadListObject initThreadList(String taskName, boolean singleFlg, boolean queueFlg) {
        synchronized (taskThreadMap) {
            ClassThreadListObject list = taskThreadMap.get(taskName);
            if(list == null) {
                list = new ClassThreadListObject(singleFlg ? 1 :limit, queueFlg);
                taskThreadMap.put(taskName, list);
            }

            return list;
        }
    }
}
