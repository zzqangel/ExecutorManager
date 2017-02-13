package src.com.cooper.appservice.comm.thread;

import com.cooper.appservice.comm.thread.data.RunnableObjectList;
import com.cooper.appservice.comm.thread.data.UserThreadObject;
import com.cooper.appservice.comm.thread.context.ServiceContext;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-12-20
 * Time: 上午10:31
 * To change this template use File | Settings | File Templates.
 * 本类是按照特殊id划分线程队列的线程类
 */
public class ServiceUserThread extends ServiceThread {

    /**
     * 本类持有的特殊id线程控制类对象,持有他的目的是在当前任务执行完毕之后，获取下一个任务，以及对相关锁进行操作调用
     * 可以认为本类和UserThreadObject类拥有主从持有关系
     */
    private RunnableObjectList runnableObjectList;

    /**
     * 构造类
     * @param context 当前类型的上下文对象
     * @param runnableObjectList 当前类持有的主类对象
     */
    public ServiceUserThread(ServiceContext context, RunnableObjectList runnableObjectList) {
        this.context = context;
        this.runnableObjectList = runnableObjectList;
    }

    public void run() {
        this.setThreadId(Thread.currentThread().getId());
        try {
            while(!isInterrupted()) {
                RunnableObject runnableObject = this.runnableObjectList.getNextQueueJob();
                if(runnableObject == null) {
                    break;
                }
                runnableObject.run(this);
                this.runnableObjectList.unLock();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            breakFlg = true;
            this.runnableObjectList.unLock();
        }
    }


}
