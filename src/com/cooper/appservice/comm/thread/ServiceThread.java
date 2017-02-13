package src.com.cooper.appservice.comm.thread;
import com.cooper.appservice.comm.thread.context.ServiceContext;
/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-12-20
 * Time: 下午2:53
 * To change this template use File | Settings | File Templates.
 * 所有任务线程类的父类
 */
public abstract class ServiceThread extends Thread implements Comparable<ServiceThread> {
    /**
     * 上下文对象
     */
    protected ServiceContext context;
    /**
     * 是否已经跳出循环的标记
     */
    protected boolean breakFlg = false;
    /**
     * 当前线程的线程id
     */
    protected long threadId;

    /**
     * 获取当前的context对象的方法
     * @return
     */
    public ServiceContext context() {
        return this.context;
    }

    public boolean isBreak() {
        return breakFlg;
    }

    protected void setThreadId(long id) {
        this.threadId = id;
    }

    public long getThreadId() {
        return this.threadId;
    }

    public void breakThread() {
        this.breakFlg = true;
    }

    public int compareTo(ServiceThread o) {
        return threadId > o.threadId ? 1 : (threadId < o.threadId ? -1 : 0);
    }
}
