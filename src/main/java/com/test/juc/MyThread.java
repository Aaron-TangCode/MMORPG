package com.test.juc;

/**
 * @ClassName MyThread
 * @Description TODO
 * @Author DELL
 * @Date 2019/9/3 10:41
 * @Version 1.0
 */
public class MyThread implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+" is running");
    }
}
