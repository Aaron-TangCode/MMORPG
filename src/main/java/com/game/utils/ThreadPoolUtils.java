package com.game.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName ThreadPoolUtils
 * @Description 线程池工具类
 * @Author DELL
 * @Date 2019/6/6 14:46
 * @Version 1.0
 */
public class ThreadPoolUtils {
    private static volatile ExecutorService threadPoolUtils = Executors.newFixedThreadPool(25);

    /**
     * 返回线程池
     * @return
     */
    public static ExecutorService getThreadPool(){
        return threadPoolUtils;
    }
}
