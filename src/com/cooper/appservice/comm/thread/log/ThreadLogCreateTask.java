package com.cooper.appservice.comm.thread.log;

import com.cooper.appservice.comm.thread.task.ServiceRunnable;
import com.cooper.appservice.comm.thread.context.ServiceContext;
import com.cooper.appservice.comm.thread.manager.RunManager;
import com.cooper.proxy.db.HiDbThreadLog;
import com.cooper.proxy.db.HiDbThreadLogDetail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-12-23
 * Time: 上午11:26
 * To change this template use File | Settings | File Templates.
 */
public class ThreadLogCreateTask implements ServiceRunnable {
    private static final HashMap<String, HiDbThreadLog> logMap = new HashMap<String, HiDbThreadLog>();

    HiDbThreadLog threadLog;
    boolean createFlg;

    public synchronized static String addStartDetailJob(HiDbThreadLogDetail threadLogDetail) {
        String taskName = threadLogDetail.getThreadClassName();
        if (taskName.equals(ThreadLogCreateTask.class.getName())) {
            return null;
        }//如果是本类，则不插入日志，否则会出现死循环，暂时先不处理 TODO
        HiDbThreadLog threadLog = logMap.get(taskName);
        boolean createFlg = false;
        if (threadLog == null) {
            threadLog = getThreadLogFromDB(taskName);
            if(threadLog == null) {
                createFlg = true;
                threadLog = new HiDbThreadLog();
                threadLog.setAverageInvokeTime(0 + "");
                threadLog.setThreadLogId(Tools.gainedNo("threadLog"));
                threadLog.setInvokeTimes(0);
                threadLog.setThreadClassName(threadLogDetail.getThreadClassName());
                threadLog.setAverageInvokeTime(0 + "");
            }
            logMap.put(taskName, threadLog);
        }
        threadLog.setDetailId(threadLogDetail.getThreadLogDetailId());
        threadLog.setInvokeTimes(threadLog.getInvokeTimes() + 1);
        threadLog.setLastInvokeDate(threadLogDetail.getInvokeStartDate());
        //平均执行时间，最后更新
        RunManager.addSingleLimitTask(new ThreadLogCreateTask(threadLog, createFlg), true, 0, null);
        return threadLog.getThreadLogId();
    }
    private static HiDbThreadLog getThreadLogFromDB(String taskName) {
        String hql = "from com.cooper.proxy.db.HiDbThreadLog where threadClassName = ?";
        List paramList = new ArrayList();
        paramList.add(taskName);
        List list = Tools.findList(hql, paramList, 1);
        if(list != null && !list.isEmpty()) {
            return (HiDbThreadLog) list.get(0);
        }
        return null;
    }
    public synchronized static void addFinishDetailJob(HiDbThreadLogDetail threadLogDetail) {
        String taskName = threadLogDetail.getThreadClassName();
        HiDbThreadLog threadLog = logMap.get(taskName);
        if(threadLog == null) {
            threadLog = getThreadLogFromDB(taskName);
            logMap.put(taskName, threadLog);
        }
        if(threadLog == null) return;
        threadLog.setLastInvokeDate(threadLogDetail.getInvokeStartDate());
        long time = Long.valueOf(threadLog.getAverageInvokeTime() == null ? "0" : threadLog.getAverageInvokeTime());
        long thisTakenTime = threadLogDetail.getInvokeEndDate().getTime() - threadLogDetail.getInvokeStartDate().getTime();
        time = (time * (threadLog.getInvokeTimes() - 1) + thisTakenTime) / threadLog.getInvokeTimes();
        threadLog.setAverageInvokeTime(time + "");
        RunManager.addSingleLimitTask(new ThreadLogCreateTask(threadLog, false), true, 0, null);
    }

    public ThreadLogCreateTask(HiDbThreadLog threadLog, boolean createFlg) {
        this.threadLog = threadLog;
        this.createFlg = createFlg;
    }

    public void run(ServiceContext context) {
        Tools.saveObject(threadLog, !createFlg);
    }

    public void stopTask(ServiceContext context) {

    }
}
