package com.cooper.proxy.comm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-12-20
 * Time: 上午8:28
 * To change this template use File | Settings | File Templates.
 * 线程池构建类
 */
public class ExecutorFactory {
    private final static ThreadGroup threadGroup = new ThreadGroup("htThreadGroup");

    private final static ConcurrentHashMap<String, ExecutorService> executorList = new ConcurrentHashMap<String, ExecutorService>();

    public static synchronized ExecutorService createExecutor(String executorName, int threadLimit, int blockingQueueSize, String threadName, RejectedExecutionHandler rejectedExecutionHandler) {
        ExecutorService executorService = executorList.get(executorName);
        if(executorService == null) {
            BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(blockingQueueSize);
            final String threadName1 = threadName;
            ThreadFactory threadFactory = new ThreadFactory() {
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(threadGroup, r);
                    thread.setDaemon(true);
                    thread.setName(threadName1 + " Thread Orders-%d");
                    thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                        public void uncaughtException(Thread t, Throwable e) {
                            e.printStackTrace();
                        }
                    });
                    return thread;
                }
            };
            if(rejectedExecutionHandler == null) {
                executorService = new ThreadPoolExecutor(threadLimit, threadLimit, 10, TimeUnit.SECONDS, queue, threadFactory);
            } else {
                executorService = new ThreadPoolExecutor(threadLimit, threadLimit, 10, TimeUnit.SECONDS, queue, threadFactory, rejectedExecutionHandler);
            }
            executorList.put(executorName, executorService);
        }
        return executorService;
    }
    private static ExecutorService appServiceExecutor = null;

    public static synchronized ExecutorService getAppServiceExecutorInstance() {
        if(appServiceExecutor == null) {
            appServiceExecutor = ExecutorFactory.createExecutor("appServiceExecutor", 10, 100, "appService", null);
        }
        return appServiceExecutor;
    }

    public synchronized static Thread getThread(long threadId) {
        Thread[] threads = new Thread[threadGroup.activeCount()];
        threadGroup.enumerate(threads);
        for(Thread t : threads) {
            if(t.getId() == threadId) {
                return t;
            }
        }
        return null;
    }
    public static Thread[] getAllThread() {
        Thread[] threads = new Thread[threadGroup.activeCount()];
        threadGroup.enumerate(threads);
        return threads;
    }
    public static void destroyExecutor() {
        try {
            List<Future> futures = new ArrayList<Future>();
            for(ExecutorService executorService : executorList.values()) {
                final ExecutorService e = executorService;
                Callable c = new Callable() {
                    public Object call() throws Exception {
                        e.shutdown();
                        return null;  //To change body of implemented methods use File | Settings | File Templates.
                    }
                };
                FutureTask<Integer> future = new FutureTask<Integer>(c);
                Thread t = new Thread(future);
                t.setDaemon(true);
                t.start();
                futures.add(future);
            }
            for(Future future : futures) {
                future.get();
            }
            Thread[] threads = getAllThread();
            for(Thread t : threads) {
                if(!t.isAlive() || t.isInterrupted()) continue;
                try {
                    t.stop();
                } catch (Exception e) {}
            }
        } catch (Exception e) {

        }
    }
}
