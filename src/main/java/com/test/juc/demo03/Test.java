package com.test.juc.demo03;

/**
 * @ClassName Test
 * @Description TODO
 * @Author DELL
 * @Date 2019/9/9 11:28
 * @Version 1.0
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {
        MyThread myThread = new MyThread();
        Thread t1 =  new Thread(myThread);
        System.out.println(t1.getName()+t1.getState());
        t1.start();
        System.out.println(t1.getName()+t1.getState());
        Thread.sleep(1000L);
        System.out.println(t1.getName()+t1.getState());
    }
}
