package com.test.juc.sleepdemo;

/**
 * @author Aaron
 * @date 2019-09-11 15:10
 * @function
 */
public class SleepTest {
    private static Object object = new Object();
    public static void main(String[] args) {
        ThreadA t1 = new ThreadA("t1");
        ThreadA t2 = new ThreadA("t2");
        t1.start();
        t2.start();
    }

    static class ThreadA extends Thread{
        public ThreadA(String name){
            super(name);
        }

        @Override
        public void run() {
            synchronized (object){
                for (int i=0;i<10;i++){
                    System.out.println(this.getName()+" "+i);
                    if(i%4==0){
                        try {
                            Thread.sleep(1000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }
    }
}
