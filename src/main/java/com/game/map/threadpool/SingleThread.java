package com.game.map.threadpool;

import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.ScheduledFuture;
import io.netty.util.concurrent.SingleThreadEventExecutor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName SingleThread
 * @Description TODO
 * @Author DELL
 * @Date 2019/6/20 14:36
 * @Version 1.0
 */
public class SingleThread extends SingleThreadEventExecutor {
    private int index;

    protected SingleThread(int index,EventExecutorGroup parent, ThreadFactory threadFactory, boolean addTaskWakesUp) {
        super(parent, threadFactory, addTaskWakesUp);
        this.index = index;
    }

    @Override
    protected void run() {
       for(;;){
           Runnable task = takeTask();
           if(task!=null){
                task.run();
           }
       }
    }

    @Override
    protected Runnable takeTask() {
        return super.takeTask();
    }

    @Override
    protected void addTask(Runnable task) {
        super.addTask(task);
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        return super.scheduleAtFixedRate(command, initialDelay, period, unit);
    }

}
