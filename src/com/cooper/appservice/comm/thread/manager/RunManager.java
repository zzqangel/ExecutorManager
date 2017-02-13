package com.cooper.appservice.comm.thread.manager;

import src.com.cooper.appservice.comm.thread.RunnableObject;
import com.cooper.appservice.comm.thread.task.ServiceCallable;
import com.cooper.appservice.comm.thread.task.ServiceRunnable;
import src.com.cooper.appservice.comm.thread.ServiceSingleThread;
import src.com.cooper.appservice.comm.thread.CallableObject;
import com.cooper.appservice.comm.thread.data.manager.ClassTaskThreadManager;
import com.cooper.appservice.comm.thread.data.SequenceThreadObject;
import com.cooper.appservice.comm.thread.data.manager.UserTaskThreadManager;
import com.cooper.appservice.comm.thread.future.ServiceFuture;
import com.cooper.appservice.comm.thread.task.UncaughtExceptionHandler;
import com.cooper.proxy.comm.ExecutorFactory;

import java.util.concurrent.Future;


/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-12-20
 * Time: 上午10:01
 * To change this template use File | Settings | File Templates.
 * 自由类型线程控制类
 */
public class RunManager {

    public static void addTask(ServiceRunnable serviceRunnable) {
        addTask(serviceRunnable, 0, null);
    }

    public static void addTask(ServiceRunnable serviceRunnable, int overTime) {
        addTask(serviceRunnable, overTime, null);
    }

    public static void addTask(ServiceRunnable serviceRunnable, int overTime, UncaughtExceptionHandler handler) {
        try {
            ExecutorFactory.getAppServiceExecutorInstance().execute(new ServiceSingleThread(createRunnableObject(serviceRunnable, overTime, true, null, handler)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> Future<T> submitRunTask(ServiceCallable<T> callable) {
        try {
            Future<T> future = ExecutorFactory.getAppServiceExecutorInstance().submit(callable);
            return future;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 添加按任务排队执行的线程
     * @param serviceRunnable
     */
    public static void addLimitTask(ServiceRunnable serviceRunnable, int overTime, UncaughtExceptionHandler handler) {
        ClassTaskThreadManager.addJob(createRunnableObject(serviceRunnable, overTime, true, null, handler), false, true);
    }

    /**
     * 添加按任务排队执行的线程
     * @param callable
     */
    public static <T> Future<T>  submitLimitTask(ServiceCallable<T> callable, int overTime) {
        return submitTask(callable, overTime, true, null, 0, null);
    }

    private static <T> Future<T>  submitTask(ServiceCallable<T> callable, int overTime, boolean queueFlg, String userId,
                                             int workType, UncaughtExceptionHandler handler) {
        CallableObject<T> callableObject = new CallableObject<T>();
        ServiceFuture<T> future = createFuture(callableObject);
        JobAdd add = getJobAdd(workType, queueFlg, userId);
        add.addJob(createRunnableObject(callableObject, callable, overTime, queueFlg, userId, handler));
        return future;
    }

    /**
     * 添加按任务排队执行的线程，只能单线程运行
     * @param serviceRunnable
     * @param queueAble 在有任务执行时，是否允许排队
     */
    public static void addSingleLimitTask(ServiceRunnable serviceRunnable, boolean queueAble, int overTime, UncaughtExceptionHandler handler) {
        ClassTaskThreadManager.addJob(createRunnableObject(serviceRunnable, overTime, queueAble, null, handler), true, queueAble);
    }

    /**
     * 添加按任务排队执行的线程
     * @param callable
     */
    public static <T> Future<T>  submitSingleLimitTask(ServiceCallable<T> callable, boolean queueAble, int overTime,
                                                       UncaughtExceptionHandler handler) {
        return submitTask(callable, overTime, queueAble, null, 1, handler);
    }

    public static void addSequenceTask(ServiceRunnable serviceRunnable, int overTime, UncaughtExceptionHandler handler) {
        new SequenceThreadObject().addJob(createRunnableObject(serviceRunnable, overTime, true, null, handler));
    }

    /**
     * 添加按任务排队执行的线程
     * @param callable
     */
    public static <T> Future<T>  submitSequenceTask(ServiceCallable<T> callable, int overTime, UncaughtExceptionHandler handler) {
        return submitTask(callable, overTime, true, null, 2, handler);
    }

    /**
     *
     * @param userId
     * @param serviceRunnable
     * @param queueFlg 是否允许排队
     */
    public static void addUserTask(String userId, ServiceRunnable serviceRunnable, boolean queueFlg, int overTime, UncaughtExceptionHandler handler) {
        String taskName = serviceRunnable.getClass().getName();
        UserTaskThreadManager.addJob(userId, taskName, createRunnableObject(serviceRunnable, overTime, queueFlg, userId, handler), queueFlg);
    }

    /**
     * 添加按任务排队执行的线程
     * @param callable
     */
    public static <T> Future<T>  submitUserTask(String userId, ServiceCallable<T> callable, boolean queueFlg, int overTime,
                                                UncaughtExceptionHandler handler) {
        return submitTask(callable, overTime, queueFlg, userId, 3, handler);
    }

    private static RunnableObject createRunnableObject(ServiceRunnable serviceRunnable, int overTime, boolean queueFlg,
                                                       String userId, UncaughtExceptionHandler handler) {
        RunnableObject runnableObject = new RunnableObject(serviceRunnable, overTime, userId, queueFlg, handler);
        return runnableObject;
    }

    private static <E> RunnableObject<E> createRunnableObject(CallableObject<E> callable, ServiceCallable<E> serviceCallable,
                                                              int overTime, boolean queueFlg, String userId,
                                                              UncaughtExceptionHandler handler) {
        RunnableObject<E> runnableObject = new RunnableObject<E>(callable, serviceCallable, overTime, userId, queueFlg, handler);
        callable.setRunnableObject(runnableObject);
        return runnableObject;
    }

    private static <E> ServiceFuture<E> createFuture(CallableObject<E> callable) {
        ServiceFuture<E> future = new ServiceFuture<E>(callable);
        return future;
    }

    interface JobAdd<E> {
        public void addJob(RunnableObject<E> runnableObject);
    }

    private static JobAdd getJobAdd(int workType, boolean queueAble, String userId) {
        final String userId1 = userId;
        final boolean queueAble1 = queueAble;
        switch (workType) {
            case 0 : {
                return new JobAdd() {
                    public void addJob(RunnableObject runnableObject) {
                        ClassTaskThreadManager.addJob(runnableObject, false, true);
                    }
                };
            }
            case 1 : {
                return new JobAdd() {
                    public void addJob(RunnableObject runnableObject) {
                        ClassTaskThreadManager.addJob(runnableObject, true, queueAble1);
                    }
                };
            }
            case 2 : {
                return new JobAdd() {
                    public void addJob(RunnableObject runnableObject) {
                        String taskName = runnableObject.getTaskName();
                        UserTaskThreadManager.addJob(userId1, taskName, runnableObject, queueAble1);
                    }
                };
            }
            case 3 : {
                return new JobAdd() {
                    public void addJob(RunnableObject runnableObject) {
                        new SequenceThreadObject().addJob(runnableObject);
                    }
                };
            }
        }
        return null;
    }

}
