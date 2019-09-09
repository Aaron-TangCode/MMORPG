package com.test.juc.demo04;

/**
 * @ClassName MyThread
 * @Description TODO
 * @Author DELL
 * @Date 2019/9/9 15:05
 * @Version 1.0
 */
public class MyThread implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+" "+Thread.currentThread().getState());
    }
}
