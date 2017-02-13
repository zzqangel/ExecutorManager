package com.cooper.appservice.comm.thread.data;

import src.com.cooper.appservice.comm.thread.RunnableObject;
import src.com.cooper.appservice.comm.thread.ServiceTaskThread;
import src.com.cooper.appservice.comm.thread.ServiceThread;
import com.cooper.appservice.comm.thread.context.ServiceContext;
import com.cooper.proxy.comm.ExecutorFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-12-21
 * Time: 下午3:04
 * To change this template use File | Settings | File Templates.
 */
public class ClassThreadListObject extends TaskObject implements Comparable {
    final ServiceContext context;

    int limit;

//    private final ConcurrentSkipListSet<ServiceTaskThread> threadList;
    RunnableObjectList runnableObjectList = new RunnableObjectList();

    ServiceThreadList threadList;
//    ServiceLockQueue<RunnableObject> serviceRunnableList = new ServiceLockQueue<RunnableObject>();

    private boolean queueFlg;

    public void setQueueFlg(boolean queueFlg) {
        if(!this.runnableObjectList.isLock()) {//如果当前线程处于未锁状态，则可以修改等待
            this.queueFlg = queueFlg;
        }

    }
    public ClassThreadListObject(int limit, boolean queueFlg)  {
        threadList = new ServiceThreadList();
        context = new ServiceContext();
        this.limit = limit;
        this.queueFlg = queueFlg;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    private ServiceTaskThread executeServiceTaskThread() {
        ServiceTaskThread serviceTaskThread = new ServiceTaskThread(context, this.runnableObjectList, threadList);
        ExecutorFactory.getAppServiceExecutorInstance().execute(serviceTaskThread);
        return serviceTaskThread;
    }

    public void removeThread(ServiceTaskThread serviceTaskThread) {
        this.threadList.remove(serviceTaskThread);
        unlockQueue();
    }

    public RunnableObject getJob() {
        if(!queueFlg) {
            runnableObjectList.lock();
        }
        return runnableObjectList.poll();
    }

    public void addJob(RunnableObject runnableObject) {
        runnableObject.setTaskObject(this);
        runnableObjectList.add(runnableObject);
        notifyThread();
    }

    public void unlockQueue() {
        if(!queueFlg) {
            runnableObjectList.unLock();
        }
    }
    /**
     * 激活线程
     */
    private void notifyThread() {
        synchronized (threadList) {
            List<ServiceThread> list = null;
            for(ServiceThread taskThread : threadList) {
                if(taskThread == null || taskThread.isBreak()) {
                    if(list == null) {
                        list = new ArrayList<ServiceThread>();
                    }
                    list.add(taskThread);
                }
            }
            if(list != null) {
                threadList.removeAll(list);
            }
            if(threadList.size() < limit) {
                this.threadList.add(executeServiceTaskThread());
            }
        }
    }

    public int compareTo(Object o) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean removeTask(RunnableObject runnableObject) {
        return this.runnableObjectList.remove(runnableObject);  //To change body of implemented methods use File | Settings | File Templates.
    }
}
