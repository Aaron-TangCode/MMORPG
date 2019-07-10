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
    private static volatile Queue<Runnable> queue = new ArrayBlockingQueue<Runnable>(20);

    private TaskQueue(){}

    public static Queue<Runnable> getQueue(){
        return queue;
    }
}
