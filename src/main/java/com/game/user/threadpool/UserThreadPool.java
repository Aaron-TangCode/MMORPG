package com.game.user.threadpool;

import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.concurrent.ThreadFactory;

/**
 * @ClassName UserThreadPool
 * @Description 用户线程池
 * @Author DELL
 * @Date 2019/6/10 20:48
 * @Version 1.0
 */
public class UserThreadPool {
    public static final int DEFAULT_THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    public static final String FACTORY_NAME = "user_thread";

//    public static final ThreadPoolExecutor[] ACCOUNT_SERVICE = new ThreadPoolExecutor[DEFAULT_THREAD_POOL_SIZE];
//
//    static {
//
//        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("userThread-%d").build();
//        for (int i = 0; i < ACCOUNT_SERVICE.length; i++) {
//            ACCOUNT_SERVICE[i] = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS, new SynchronousQueue<>());
//        }
//    }

    public static final SingleThread[] ACCOUNT_SERVICE = new SingleThread[DEFAULT_THREAD_POOL_SIZE];
    static {
        ThreadFactory threadFactory = new DefaultThreadFactory(FACTORY_NAME);
        for (int i=0;i<ACCOUNT_SERVICE.length;i++){
            ACCOUNT_SERVICE[i] = new SingleThread(i+1,null,threadFactory,true);
        }
    }
}

