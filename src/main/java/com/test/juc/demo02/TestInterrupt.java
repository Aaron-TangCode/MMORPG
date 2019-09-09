package com.test.juc.demo02;

/**
 * @ClassName TestInterrupt
 * @Description TODO
 * @Author DELL
 * @Date 2019/9/9 9:40
 * @Version 1.0
 */
public class TestInterrupt {
    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        Thread t1 = new Thread(myThread);
        t1.start();
        t1.interrupt();
    }
}
