package com.test.juc.demo03;

/**
 * @ClassName MyThread
 * @Description TODO
 * @Author DELL
 * @Date 2019/9/9 11:26
 * @Version 1.0
 */
public class MyThread implements Runnable {
    @Override
    public void run() {
        System.out.println("thread is run over");
    }
}
