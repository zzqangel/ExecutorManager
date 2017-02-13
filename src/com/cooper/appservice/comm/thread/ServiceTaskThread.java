package src.com.cooper.appservice.comm.thread;

import com.cooper.appservice.comm.thread.data.ClassThreadListObject;
import com.cooper.appservice.comm.thread.context.ServiceContext;
import com.cooper.appservice.comm.thread.data.RunnableObjectList;
import com.cooper.appservice.comm.thread.data.ServiceThreadList;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-12-21
 * Time: ÏÂÎç1:41
 * To change this template use File | Settings | File Templates.
 */
public class ServiceTaskThread extends ServiceThread {
    private RunnableObjectList runnableObjectList;
    private ServiceThreadList serviceThreadList;

    public ServiceTaskThread(ServiceContext context, RunnableObjectList runnableObjectList, ServiceThreadList serviceThreadList) {
        this.runnableObjectList = runnableObjectList;
        this.context = context;
        this.serviceThreadList = serviceThreadList;
    }

    public void run() {
        this.setThreadId(Thread.currentThread().getId());
        try {
            while (!isInterrupted()) {
                RunnableObject runnableObject = runnableObjectList.poll();
                if (runnableObject == null) break;
                runnableObject.run(this);
                runnableObjectList.unLock();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            breakFlg = true;
            remove();
        }
    }

    private void remove() {
        this.serviceThreadList.removeThread(this);
    }
}
