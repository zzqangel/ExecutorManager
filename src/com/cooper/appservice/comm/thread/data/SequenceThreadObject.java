package com.cooper.appservice.comm.thread.data;

import src.com.cooper.appservice.comm.thread.RunnableObject;
import src.com.cooper.appservice.comm.thread.ServiceSequenceThread;
import com.cooper.appservice.comm.thread.context.ServiceContext;
import com.cooper.proxy.comm.ExecutorFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-12-27
 * Time: 下午1:40
 * To change this template use File | Settings | File Templates.
 * 单任务排队执行的线程类的数据提供类，所有需要执行的任务加入到本类中，然后提供给线程类进行执行
 * 本类中包含一个RunnableObject对象构建的序列对象，所有的任务会存储在本类中
 * 本类中还持有
 */
public class SequenceThreadObject extends TaskObject {

    private final static RunnableObjectList runList = new RunnableObjectList();
    private static ServiceSequenceThread serviceSequenceThread;
    @Override
    public boolean removeTask(RunnableObject runnableObject) {
        return runList.remove(runnableObject);
    }

    @Override
    public void addJob(RunnableObject runnableObject) {
        runnableObject.setTaskObject(this);
        runList.add(runnableObject);
        notifyThread();
    }

    public synchronized static void notifyThread() {
        if(serviceSequenceThread == null || serviceSequenceThread.isBreak()) {
            final ServiceContext context1 = serviceSequenceThread == null ? new ServiceContext() : serviceSequenceThread.context();
            serviceSequenceThread = new ServiceSequenceThread(context1, runList);
            serviceSequenceThread.setDaemon(true);
            ExecutorFactory.getAppServiceExecutorInstance().execute(serviceSequenceThread);
        }
    }

}
