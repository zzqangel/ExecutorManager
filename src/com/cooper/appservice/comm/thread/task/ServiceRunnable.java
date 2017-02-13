package com.cooper.appservice.comm.thread.task;
import com.cooper.appservice.comm.thread.context.ServiceContext;
/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-12-20
 * Time: 上午8:19
 * To change this template use File | Settings | File Templates.
 * 线程调用接口
 */
public interface ServiceRunnable extends ServiceRunInterface {
    /**
     * 当当前任务被调用时，会调用此方法
     * @param context context是线程调用的上下文
     */
    public void run(ServiceContext context);


}
