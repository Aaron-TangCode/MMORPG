package com.game.map.threadpool;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @ClassName TaskQueue
 * @Description TODO
 * @Author DELL
 * @Date 2019/7/8 17:12
 * @Version 1.0
 */
public class TaskQueue {
    private  volatile Queue<Runnable> queue;

    private TaskQueue(){}

    public static Queue<Runnable> getQueue(){
        return new ArrayBlockingQueue<Runnable>(20);
    }
}
