package com.cooper.appservice.comm.thread.watch;

import src.com.cooper.appservice.comm.thread.RunnableObject;
import org.apache.http.annotation.GuardedBy;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-12-22
 * Time: 上午11:20
 * To change this template use File | Settings | File Templates.
 * 本类是线程监控类的线程类
 * 本类中的监控停止动作应该进行进一步的拆分，将其行为封装到另一个类对象中，使得本类集中在线程动作中
 * 本类中的线程停止动作可能带来意外风险，需要进行评估
 * 本类中持有所有任务对象的列表对象
 * 线程监控的循环时间是1分钟
 * 如果过程中遇到了阻碍，则会计算剩余时间进行扣除
 * 所有任务类执行完毕之后，本类会停止循环
 *
 */
public class ServiceRunWatchThread extends Thread {


    @GuardedBy("this")
    private static ServiceRunWatchThread serviceRunWatchThread;

    private ServiceRunWatchThread() {}

    public synchronized static void addObject(RunnableObject runnableObject) {
        ServiceRunObjectList.addJob(runnableObject);
        if(serviceRunWatchThread == null || !serviceRunWatchThread.isAlive() || serviceRunWatchThread.isInterrupted()) {
            serviceRunWatchThread = new ServiceRunWatchThread();
            serviceRunWatchThread.setDaemon(true);
            serviceRunWatchThread.start();
        }
    }



    public void run() {
        long startTime = 0;
        long endTime = 0;
        while (true) {
            long waitTime = 60 * 1000 - (endTime - startTime);
            try {
                if(waitTime > 0) {
                    Thread.sleep(waitTime);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            List<RunnableObject> list;
            list = ServiceRunObjectList.getRunnableList();
            if(list.isEmpty()) {
                break;
            }
            startTime = System.currentTimeMillis();
            for(RunnableObject runnableObject : list) {
                ServiceRunWatchHandler.checkRunnableObject(runnableObject);
            }
            endTime = System.currentTimeMillis();
        }
    }
}
