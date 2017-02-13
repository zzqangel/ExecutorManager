package com.cooper.appservice.comm.thread.task;

import com.cooper.appservice.comm.thread.context.ServiceContext;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-12-26
 * Time: 下午5:21
 * To change this template use File | Settings | File Templates.
 */
public interface ServiceRunInterface {
    /**
     * 停止当前正在执行的任务
     */
    public void stopTask(ServiceContext context);
}
