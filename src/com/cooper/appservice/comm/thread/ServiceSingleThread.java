package src.com.cooper.appservice.comm.thread;

import com.cooper.appservice.comm.thread.future.ServiceFuture;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-12-20
 * Time: ÉÏÎç9:58
 * To change this template use File | Settings | File Templates.
 */
public class ServiceSingleThread<E> extends ServiceThread {
    RunnableObject runnableObject;
    private ServiceFuture future;
    public ServiceSingleThread(RunnableObject runnableObject) {
        this.runnableObject = runnableObject;
    }



    public void run() {
        this.setThreadId(Thread.currentThread().getId());
        try {
            this.runnableObject.run(this);
        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}
