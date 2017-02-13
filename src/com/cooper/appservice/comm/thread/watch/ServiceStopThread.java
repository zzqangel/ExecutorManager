package com.cooper.appservice.comm.thread.watch;

import src.com.cooper.appservice.comm.thread.RunnableObject;
import com.cooper.appservice.comm.thread.task.ServiceRunnable;
import com.cooper.appservice.comm.thread.context.ServiceContext;
import org.apache.http.annotation.GuardedBy;

public class ServiceStopThread extends Thread implements ServiceRunnable {
    @GuardedBy("this")
    RunnableObject runnableObject;
    public ServiceStopThread(RunnableObject runnableObject) {
        this.runnableObject = runnableObject;
    }
    public void run() {
        runnableObject.stopTask();
    }

    public void run(ServiceContext context) {
        this.run();
    }

    public void stopTask(ServiceContext context) {
    }
}