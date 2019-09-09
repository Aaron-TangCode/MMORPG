package com.test.juc.demo04;

/**
 * @ClassName Test
 * @Description TODO
 * @Author DELL
 * @Date 2019/9/9 15:05
 * @Version 1.0
 */
public class Test {
    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        Thread t1 = new Thread(myThread);

        t1.run();

        t1.start();
    }
}
