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
    public static final int DEFAULT_THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    public static final String FACTORY_NAME = "map_thread";

    public static final SingleThread[] ACCOUNT_SERVICE = new SingleThread[DEFAULT_THREAD_POOL_SIZE];
    static {
        ThreadFactory threadFactory = new DefaultThreadFactory(FACTORY_NAME);
        for (int i=0;i<ACCOUNT_SERVICE.length;i++){
            ACCOUNT_SERVICE[i] = new SingleThread(i+1,null,threadFactory,true);
        }
    }
    //对外开放接口
    public static SingleThread getThreadPool(int index){
        return ACCOUNT_SERVICE[index];
    }

    //计算线程序号
    public static int getThreadIndex(Object number){
        int id = Math.abs(number.hashCode());
        return id% DEFAULT_THREAD_POOL_SIZE;
    }
}

