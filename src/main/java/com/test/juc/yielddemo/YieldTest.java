package com.test.juc.yielddemo;

/**
 * @author Aaron
 * @date 2019-09-11 14:26
 * @function
 */
public class YieldTest {
    static Object obj = new Object();
    public static void main(String[] args) {
        ThreadA t1 = new ThreadA("t1");
        ThreadA t2 = new ThreadA("t2");
        t1.start();
        t2.start();
    }

   static class ThreadA extends  Thread{
        public ThreadA(String name){
            super(name);
        }
        @Override
        public void run() {
            synchronized (obj){
                for (int i=0;i<10;i++){
                    System.out.println(this.getName()+" "+i);
                    if(i%4==0){
                        yield();
                    }
                }

            }
        }
    }
}

