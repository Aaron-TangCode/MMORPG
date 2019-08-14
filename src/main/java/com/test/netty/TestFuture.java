package com.test.netty;

import io.netty.util.concurrent.DefaultEventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;

/**
 * @ClassName TestFuture
 * @Description TODO
 * @Author DELL
 * @Date 2019/8/14 17:18
 * @Version 1.0
 */
public class TestFuture {
    public static void main(String[] args) {
        final DefaultEventExecutor wang = new DefaultEventExecutor();
        final DefaultEventExecutor li = new DefaultEventExecutor();
        wang.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(
                        Thread.currentThread().getName() + " 1. 这是一道简单的题");
            }
        });

        wang.execute(new Runnable() {
            @Override
            public void run() {
                Promise<Integer> promise = wang.newPromise();
                promise.addListener(new GenericFutureListener<Future<? super Integer>>() {
                    @Override
                    public void operationComplete(Future<? super Integer> future) throws Exception {
                        System.out.println(Thread.currentThread().getName() + "复杂题执行结果");
                    }
                });
                li.execute(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(
                                Thread.currentThread().getName() + " 2. 这是一道复杂的题");
                    }
                });
            }
        });

        wang.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(
                        Thread.currentThread().getName() + " 3. 这是一道简单的题");
            }
        });
    }



}
