package com.game.user.threadpool;

import java.util.concurrent.*;

/**
 * @ClassName UserThreadPool
 * @Description 用户线程池
 * @Author DELL
 * @Date 2019/6/10 20:48
 * @Version 1.0
 */
public class UserThreadPool {
    public static final int DEFAULT_THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    public static final ThreadPoolExecutor[] ACCOUNT_SERVICE = new ThreadPoolExecutor[DEFAULT_THREAD_POOL_SIZE];

    static {
        for (int i = 0; i < ACCOUNT_SERVICE.length; i++) {
            ACCOUNT_SERVICE[i] = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS, new SynchronousQueue<>(), new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    return null;
                }
            });
        }
    }
}
