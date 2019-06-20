package com.test.testmysql;

import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.RejectedExecutionHandler;
import io.netty.util.concurrent.SingleThreadEventExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

/**
 * @ClassName TestNetty
 * @Description TODO
 * @Author DELL
 * @Date 2019/6/20 12:15
 * @Version 1.0
 */
public class TestNetty extends SingleThreadEventExecutor {

    protected TestNetty(EventExecutorGroup parent, ThreadFactory threadFactory, boolean addTaskWakesUp) {
        super(parent, threadFactory, addTaskWakesUp);
    }

    protected TestNetty(EventExecutorGroup parent, ThreadFactory threadFactory, boolean addTaskWakesUp, int maxPendingTasks, RejectedExecutionHandler rejectedHandler) {
        super(parent, threadFactory, addTaskWakesUp, maxPendingTasks, rejectedHandler);
    }

    protected TestNetty(EventExecutorGroup parent, Executor executor, boolean addTaskWakesUp) {
        super(parent, executor, addTaskWakesUp);
    }

    protected TestNetty(EventExecutorGroup parent, Executor executor, boolean addTaskWakesUp, int maxPendingTasks, RejectedExecutionHandler rejectedHandler) {
        super(parent, executor, addTaskWakesUp, maxPendingTasks, rejectedHandler);
    }

    @Override
    protected void run() {

    }


}
