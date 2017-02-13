package com.cooper.appservice.comm.thread.log;

import com.cooper.appservice.comm.thread.task.ServiceRunnable;
import com.cooper.appservice.comm.thread.context.ServiceContext;
import com.cooper.appservice.comm.thread.manager.RunManager;
import com.cooper.proxy.db.HiDbThreadLogDetail;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-12-23
 * Time: 下午1:24
 * To change this template use File | Settings | File Templates.
 */
public class ThreadLogDetailCreateTask implements ServiceRunnable {
    private HiDbThreadLogDetail threadLogDetail;

    public ThreadLogDetailCreateTask(HiDbThreadLogDetail threadLogDetail) {
        this.threadLogDetail = threadLogDetail;
    }

    public static void addDetailTask(HiDbThreadLogDetail threadLogDetail) {
        RunManager.addSingleLimitTask(new ThreadLogDetailCreateTask(threadLogDetail), true, 0, null);
    }

    public void run(ServiceContext context) {
        try {
            if(this.threadLogDetail.getThreadLogDetailId() == null) {
                this.threadLogDetail.setThreadLogDetailId(Tools.gainedNo("threadLD"));
                if(threadLogDetail.getInvokeStartDate() == null) {
                    threadLogDetail.setInvokeStartDate(new Date());
                }
                String id = ThreadLogCreateTask.addStartDetailJob(this.threadLogDetail);
                if(id == null) return;//如果没有主表对象，则不插入
                this.threadLogDetail.setThreadLogId(id);
                Tools.saveObject(this.threadLogDetail, false);
            } else {
                if(threadLogDetail.getThreadLogId() == null) return;//如果没有主表对象，则不插入
                if(threadLogDetail.getInvokeEndDate() == null) {
                    threadLogDetail.setInvokeEndDate(new Date());
                }
                ThreadLogCreateTask.addFinishDetailJob(threadLogDetail);
                Tools.saveObject(this.threadLogDetail, true);
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void stopTask(ServiceContext context) {

    }
}
