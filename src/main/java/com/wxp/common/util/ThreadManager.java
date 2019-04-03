package com.wxp.common.util;

import java.util.UUID;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ff
 * @date 2018/2/5 9:50
 */
public class ThreadManager {
    /**
     *    通过ThreadPoolExecutor的代理类来对线程池的管理
     */
    private static ThreadPoolProxy mThreadPoolProxy;

    // 单列对象
    public static ThreadPoolProxy getThreadPollProxy() {
        synchronized (ThreadPoolProxy.class) {
            if (mThreadPoolProxy == null) {
                mThreadPoolProxy = new ThreadPoolProxy(8, 16, 1000);
            }
        }
        return mThreadPoolProxy;
    }

    public static class ThreadPoolProxy{
        //线程执行者，java内部通过api实现对线程池的管理
        private ThreadPoolExecutor poolExecutor;
        private int corePoolSize;
        private int maximumPoolSize;
        private long keepAliveTime;

        public ThreadPoolProxy(int corePoolSize,int maximumPoolSize,long keepAliveTime){
            this.corePoolSize = corePoolSize;
            this.maximumPoolSize = maximumPoolSize;
            this.keepAliveTime = keepAliveTime;
        }

        public void execute(Runnable r){
            if (poolExecutor == null || poolExecutor.isShutdown()){
                poolExecutor = new ThreadPoolExecutor(
//                        核心线程数量
                        corePoolSize,
//                        最大线程数量
                        maximumPoolSize,
//                        当线程空闲时，保持活跃的时间
                        keepAliveTime,
//                        时间单元，单位毫秒
                        TimeUnit.SECONDS,
//                        线程任务队列
                        new LinkedBlockingDeque<Runnable>(),
//                        创建线程工厂
                       new MyFactory()
                );
                poolExecutor.execute(r);
            }
        }
    }

    public static class MyFactory implements ThreadFactory{
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("ThreadName:"+ UUID.randomUUID().toString().substring(0,3));
            return thread;
        }
    }

}
