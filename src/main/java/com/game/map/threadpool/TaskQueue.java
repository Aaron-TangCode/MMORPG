package com.game.map.threadpool;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @ClassName TaskQueue
 * @Description 任务队列
 * @Author DELL
 * @Date 2019/7/8 17:12
 * @Version 1.0
 */
public class TaskQueue {
    /**
     * 任务队列
     */
    private  volatile Queue<Runnable> queue;

    private TaskQueue(){}

    /**
     * 返回任务队列对象
     * @return 对象
     */
    public static Queue<Runnable> getQueue(){
        return new ArrayBlockingQueue<Runnable>(20);
    }
}
