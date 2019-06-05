package com.game.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName ThreadUtils
 * @Description TODO
 * @Author DELL
 * @Date 2019/6/319:59
 * @Version 1.0
 */
public class ThreadUtils {
    private ThreadUtils(){}
    private static volatile ExecutorService threadPool = null;
    public static ExecutorService getThreadPool(){
        if(threadPool==null){
            synchronized (ThreadUtils.class){
                if (threadPool==null){
                    threadPool = Executors.newFixedThreadPool(20);
                }
            }
        }
        return threadPool;
    }
}
