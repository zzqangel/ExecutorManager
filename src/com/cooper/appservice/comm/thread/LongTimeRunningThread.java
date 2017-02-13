package src.com.cooper.appservice.comm.thread;

import com.cooper.appservice.comm.thread.context.ServiceContext;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-12-23
 * Time: 下午5:16
 * To change this template use File | Settings | File Templates.
 * 循环执行的线程应该继承本对象
 */
public abstract class LongTimeRunningThread {

    protected boolean breakFlg;

    public boolean getBreakFlg() {
        return this.breakFlg;
    }

    protected void breakRunning() {
        this.breakFlg = true;
    }

    /**
     * 如果需要获取context里面的参数，需要重写本方法
     * @param context
     */
    public void stopTask(ServiceContext context) {
        this.breakRunning();
    }
}
