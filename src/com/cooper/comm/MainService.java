package com.cooper.comm;

import com.cooper.appservice.comm.thread.task.ServiceCallable;
import com.cooper.appservice.comm.thread.task.ServiceRunnable;
import com.cooper.appservice.comm.thread.manager.RunManager;
import com.cooper.appservice.comm.thread.task.UncaughtExceptionHandler;

import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-12-20
 * Time: 上午8:12
 * To change this template use File | Settings | File Templates.
 */
public class MainService {

    public MainService() {
        super();
    }


    /*********线程调用开始*******/
    /**
     * 添加普通线程任务
     * @param serviceRunnable 任务对象，需要进行实现
     */
    public void addRunTask(ServiceRunnable serviceRunnable) {
        addRunTask(serviceRunnable, 0);
    }

    /**
     * 按照用户添加该用户该任务排队执行的任务
     * @param serviceRunnable
     * @param userId 该任务的所属用户id
     * @param queueAble 该任务是否允许排队，如果不允许排队，当前任务执行完成前，后续任务添加进来会被抛弃，如果允许排队，则会被加入队列中
     */
    public void addUserRunTask(ServiceRunnable serviceRunnable, String userId, boolean queueAble) {
        addUserRunTask(serviceRunnable, userId, queueAble, 0);
    }

    /**
     * 按照任务类型添加有限数量的多线程任务
     * @param serviceRunnable
     */
    public void addClassLimitRunTask(ServiceRunnable serviceRunnable) {
        addClassLimitRunTask(serviceRunnable, 0);
    }
    /**
     * 按照任务类型添加唯一数量的多线程任务，只有一个线程排队执行
     * @param serviceRunnable
     * @param queueAble 在任务执行中，是否允许添加新的任务，如果为false，则新加入的任务会被抛弃
     */
    public void addClassSingleLimitRunTask(ServiceRunnable serviceRunnable, boolean queueAble) {
        addClassSingleLimitRunTask(serviceRunnable, queueAble, 0);
    }
    /**
     * 添加多任务单队列执行线程
     * @param serviceRunnable
     */
    public void addSequenceRunTask(ServiceRunnable serviceRunnable) {
        addSequenceRunTask(serviceRunnable, 0);
    }

    /**
     * 添加普通线程任务
     * @param serviceRunnable 任务对象，需要进行实现
     * @param overTime 任务超时时间，单位是秒，超过此时间，任务会调用stop方法，超过一定时间不能停止，任务会强制终止
     */
    public void addRunTask(ServiceRunnable serviceRunnable, int overTime) {
        RunManager.addTask(serviceRunnable, overTime);
    }

    /**
     * 按照用户添加该用户该任务排队执行的任务
     * @param serviceRunnable
     * @param userId 该任务的所属用户id
     * @param queueAble 该任务是否允许排队，如果不允许排队，当前任务执行完成前，后续任务添加进来会被抛弃，如果允许排队，则会被加入队列中
     * @param overTime 任务超时时间，单位是秒，超过此时间，任务会调用stop方法，超过一定时间不能停止，任务会强制终止
     */
    public void addUserRunTask(ServiceRunnable serviceRunnable, String userId, boolean queueAble, int overTime) {
        RunManager.addUserTask(userId, serviceRunnable, queueAble, overTime, null);
    }
    /**
     * 按照用户添加该用户该任务排队执行的任务
     * @param serviceRunnable
     * @param userId 该任务的所属用户id
     * @param queueAble 该任务是否允许排队，如果不允许排队，当前任务执行完成前，后续任务添加进来会被抛弃，如果允许排队，则会被加入队列中
     * @param overTime 任务超时时间，单位是秒，超过此时间，任务会调用stop方法，超过一定时间不能停止，任务会强制终止
     * @param handler 异常处理接口类
     */
    public void addUserRunTask(ServiceRunnable serviceRunnable, String userId, boolean queueAble, int overTime, UncaughtExceptionHandler handler) {
        RunManager.addUserTask(userId, serviceRunnable, queueAble, overTime, handler);
    }
    /**
     * 按照任务类型添加有限数量的多线程任务
     * @param serviceRunnable
     * @param overTime 任务超时时间，单位是秒，超过此时间，任务会调用stop方法，超过一定时间不能停止，任务会强制终止
     */
    public void addClassLimitRunTask(ServiceRunnable serviceRunnable, int overTime) {
        RunManager.addLimitTask(serviceRunnable, overTime, null);
    }
    /**
     * 按照任务类型添加有限数量的多线程任务
     * @param serviceRunnable
     * @param overTime 任务超时时间，单位是秒，超过此时间，任务会调用stop方法，超过一定时间不能停止，任务会强制终止
     * @param handler 异常处理接口类
     */
    public void addClassLimitRunTask(ServiceRunnable serviceRunnable, int overTime, UncaughtExceptionHandler handler) {
        RunManager.addLimitTask(serviceRunnable, overTime, handler);
    }
    /**
     * 按照任务类型添加唯一数量的多线程任务，只有一个线程排队执行
     * @param serviceRunnable
     * @param queueAble 在任务执行中，是否允许添加新的任务，如果为false，则新加入的任务会被抛弃
     * @param overTime 任务超时时间，单位是秒，超过此时间，任务会调用stop方法，超过一定时间不能停止，任务会强制终止
     */
    public void addClassSingleLimitRunTask(ServiceRunnable serviceRunnable, boolean queueAble, int overTime) {
        RunManager.addSingleLimitTask(serviceRunnable, queueAble, overTime, null);
    }
    /**
     * 按照任务类型添加唯一数量的多线程任务，只有一个线程排队执行
     * @param serviceRunnable
     * @param queueAble 在任务执行中，是否允许添加新的任务，如果为false，则新加入的任务会被抛弃
     * @param overTime 任务超时时间，单位是秒，超过此时间，任务会调用stop方法，超过一定时间不能停止，任务会强制终止
     * @param handler 异常处理接口类
     */
    public void addClassSingleLimitRunTask(ServiceRunnable serviceRunnable, boolean queueAble, int overTime,
                                           UncaughtExceptionHandler handler) {
        RunManager.addSingleLimitTask(serviceRunnable, queueAble, overTime, handler);
    }
    /**
     * 添加多任务单队列执行线程
     * @param serviceRunnable
     * @param overTime 任务超时时间，单位是秒，超过此时间，任务会调用stop方法，超过一定时间不能停止，任务会强制终止
     */
    public void addSequenceRunTask(ServiceRunnable serviceRunnable, int overTime) {
        RunManager.addSequenceTask(serviceRunnable, overTime, null);
    }
    /**
     * 添加多任务单队列执行线程
     * @param serviceRunnable
     * @param overTime 任务超时时间，单位是秒，超过此时间，任务会调用stop方法，超过一定时间不能停止，任务会强制终止
     * @param handler 异常处理类
     */
    public void addSequenceRunTask(ServiceRunnable serviceRunnable, int overTime, UncaughtExceptionHandler handler) {
        RunManager.addSequenceTask(serviceRunnable, overTime, handler);
    }
    public <T> Future<T> submitRunTask(ServiceCallable<T> callable) {
        return RunManager.submitRunTask(callable);
    }

    /**
     * 按照任务类型添加有限数量的多线程任务
     * @param callable
     */
    public <T> Future<T> submitClassLimitRunTask(ServiceCallable<T> callable) {
        return submitClassLimitRunTask(callable, 0);
    }

    /**
     * 按照任务类型添加有限数量的多线程任务
     * @param callable
     */
    public <T> Future<T> submitClassLimitRunTask(ServiceCallable<T> callable, int overTime) {
        return RunManager.submitLimitTask(callable, overTime);
    }

    /**
     * 按照任务类型添加唯一数量的多线程任务，只有一个线程排队执行
     * @param callable
     * @param queueAble 在任务执行中，是否允许添加新的任务，如果为false，则新加入的任务会被抛弃
     */
    public <T> Future<T> submitClassSingleLimitRunTask(ServiceCallable<T> callable, boolean queueAble) {
        return submitClassSingleLimitRunTask(callable, queueAble, 0);
    }

    /**
     * 按照任务类型添加唯一数量的多线程任务，只有一个线程排队执行
     * @param callable
     * @param queueAble 在任务执行中，是否允许添加新的任务，如果为false，则新加入的任务会被抛弃
     */
    public <T> Future<T> submitClassSingleLimitRunTask(ServiceCallable<T> callable, boolean queueAble, int overTime) {
        return RunManager.submitSingleLimitTask(callable, queueAble, overTime, null);
    }
    /**
     * 按照任务类型添加唯一数量的多线程任务，只有一个线程排队执行
     * @param callable
     * @param queueAble 在任务执行中，是否允许添加新的任务，如果为false，则新加入的任务会被抛弃
     * @param overTime 等待超时时间
     * @param handler 异常处理接口类
     */
    public <T> Future<T> submitClassSingleLimitRunTask(ServiceCallable<T> callable, boolean queueAble, int overTime, UncaughtExceptionHandler handler) {
        return RunManager.submitSingleLimitTask(callable, queueAble, overTime, handler);
    }
    /**
     * 添加多任务单队列执行线程
     * @param callable
     */
    public <T> Future<T> submitSequenceRunTask(ServiceCallable<T> callable) {
        return submitSequenceRunTask(callable, 0);
    }
    /**
     * 添加多任务单队列执行线程
     * @param callable
     * @param overTime 任务超时时间，单位是秒，超过此时间，任务会调用stop方法，超过一定时间不能停止，任务会强制终止
     */
    public <T> Future<T> submitSequenceRunTask(ServiceCallable<T> callable, int overTime) {
        return RunManager.submitSequenceTask(callable, overTime, null);

    }
    /**
     * 添加多任务单队列执行线程
     * @param callable
     * @param overTime 任务超时时间，单位是秒，超过此时间，任务会调用stop方法，超过一定时间不能停止，任务会强制终止
     * @param handler 异常处理接口类
     */
    public <T> Future<T> submitSequenceRunTask(ServiceCallable<T> callable, int overTime, UncaughtExceptionHandler handler) {
        return RunManager.submitSequenceTask(callable, overTime, handler);

    }
    /**
     * 按照用户添加该用户该任务排队执行的任务
     * @param callable
     * @param userId 该任务的所属用户id
     * @param queueAble 该任务是否允许排队，如果不允许排队，当前任务执行完成前，后续任务添加进来会被抛弃，如果允许排队，则会被加入队列中
     */
    public <T> Future<T> submitUserRunTask(ServiceCallable<T> callable, String userId, boolean queueAble) {
        return submitUserRunTask(callable, userId, queueAble, 0);
    }
    /**
     * 按照用户添加该用户该任务排队执行的任务
     * @param callable
     * @param userId 该任务的所属用户id
     * @param queueAble 该任务是否允许排队，如果不允许排队，当前任务执行完成前，后续任务添加进来会被抛弃，如果允许排队，则会被加入队列中
     * @param overTime 任务超时时间，单位是秒，超过此时间，任务会调用stop方法，超过一定时间不能停止，任务会强制终止
     */
    public <T> Future<T> submitUserRunTask(ServiceCallable<T> callable, String userId, boolean queueAble, int overTime) {
        return RunManager.submitUserTask(userId, callable, queueAble, overTime, null);
    }
    /**
     * 按照用户添加该用户该任务排队执行的任务
     * @param callable
     * @param userId 该任务的所属用户id
     * @param queueAble 该任务是否允许排队，如果不允许排队，当前任务执行完成前，后续任务添加进来会被抛弃，如果允许排队，则会被加入队列中
     * @param overTime 任务超时时间，单位是秒，超过此时间，任务会调用stop方法，超过一定时间不能停止，任务会强制终止
     */
    public <T> Future<T> submitUserRunTask(ServiceCallable<T> callable, String userId, boolean queueAble, int overTime,
                                           UncaughtExceptionHandler handler) {
        return RunManager.submitUserTask(userId, callable, queueAble, overTime, handler);
    }
    /*********线程调用结束*******/

}
