package com.cooper.appservice.comm.thread.task;

import com.cooper.appservice.comm.thread.context.ServiceContext;

import java.util.concurrent.Callable;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-12-26
 * Time: 下午4:40
 * To change this template use File | Settings | File Templates.
 * 实现了call和stop方法的父类对象
 * 用户在需要future返回对象时，需要实现本类。
 * 本类中call(Context)方法是本类中需要子类进行实现的方法
 * 本类的call方法被定义为了final，即不允许子类对其进行重写
 * call方法实际是对Callable接口方法的再重写
 */
public abstract class ServiceCallable<E> implements Callable<E>, ServiceRunInterface {

    public abstract E call(ServiceContext context);

    final public E call() throws Exception {
        return call(new ServiceContext());
    }
}
