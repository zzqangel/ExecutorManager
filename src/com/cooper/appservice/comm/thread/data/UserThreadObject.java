package com.cooper.appservice.comm.thread.data;

import src.com.cooper.appservice.comm.thread.RunnableObject;
import src.com.cooper.appservice.comm.thread.ServiceUserThread;
import com.cooper.appservice.comm.thread.context.ServiceContext;
import com.cooper.proxy.comm.ExecutorFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-12-21
 * Time: 下午3:58
 * To change this template use File | Settings | File Templates.
 */
public class UserThreadObject extends TaskObject {

    private ServiceUserThread serviceUserThread;

    private RunnableObjectList serviceRunnableList;

    final private ServiceContext context;

    private boolean queueFlg;//是否允许排队

    public void setQueueFlg(boolean queueFlg) {
        if(!this.serviceRunnableList.isLock()) {//如果当前线程处于未锁状态，则可以修改等待
            this.queueFlg = queueFlg;
        }

    }

    public UserThreadObject(boolean queueFlg) {
        this.serviceRunnableList = new RunnableObjectList();
        this.context = new ServiceContext();
        this.queueFlg = queueFlg;
        this.serviceRunnableList.setQueueFlg(queueFlg);
    }

    public synchronized void notifyThread() {
        if(this.serviceUserThread == null || this.serviceUserThread.isBreak()) {
            this.serviceUserThread = new ServiceUserThread(context, this.serviceRunnableList);
            ExecutorFactory.getAppServiceExecutorInstance().execute(this.serviceUserThread);
        }
    }

    /*public RunnableObject getNextJob() {
        if(!queueFlg) {
            serviceRunnableList.lock();
        }
        return serviceRunnableList.poll();
    }*/

    public void addJob(RunnableObject runnableObject) {
        runnableObject.setTaskObject(this);
        this.serviceRunnableList.add(runnableObject);
        notifyThread();
    }

    public void unlockQueue() {
        if(!queueFlg) {
            this.serviceRunnableList.unLock();
        }
    }

    @Override
    public boolean removeTask(RunnableObject runnableObject) {
        return serviceRunnableList.remove(runnableObject);
    }
}
