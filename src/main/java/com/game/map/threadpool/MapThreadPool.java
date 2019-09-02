package com.game.map.threadpool;

import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.concurrent.ThreadFactory;

/**
 * @ClassName MapThreadPool
 * @Description 环境线程池
 * @Author DELL
 * @Date 2019/6/10 20:48
 * @Version 1.0
 */
public class MapThreadPool {
    /**
     * 线程池默认大小
     */
    public static final int DEFAULT_THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    /**
     * 线程池的工厂名字
     */
    public static final String FACTORY_NAME = "map_thread";

    public static final SingleThread[] ACCOUNT_SERVICE = new SingleThread[DEFAULT_THREAD_POOL_SIZE];
    static {
        ThreadFactory threadFactory = new DefaultThreadFactory(FACTORY_NAME);
        for (int i=0;i<ACCOUNT_SERVICE.length;i++){
            ACCOUNT_SERVICE[i] = new SingleThread(i+1,null,threadFactory,true);
        }
    }

    /**
     * 对外开放接口
     * @param index 坐标
     * @return 线程
     */
    public static SingleThread getThreadPool(int index){
        return ACCOUNT_SERVICE[index];
    }

    /**
     * 计算线程序号
     * @param number 序号
     * @return 序号
     */
    public static int getThreadIndex(Object number){
        int id = Math.abs(number.hashCode());
        return id% DEFAULT_THREAD_POOL_SIZE;
    }
}

