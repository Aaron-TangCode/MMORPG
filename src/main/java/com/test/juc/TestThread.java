package com.test.juc;

/**
 * @ClassName TestThread
 * @Description TODO
 * @Author DELL
 * @Date 2019/9/3 10:40
 * @Version 1.0
 */
public class TestThread {

    public static void main(String[] args) {
        MyThread r = new MyThread();
        Thread t1 = new Thread(r);
        t1.start();
        t1.start();
    }
}
