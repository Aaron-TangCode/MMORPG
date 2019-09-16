package com.test.juc.damondemo;

/**
 * @author Aaron
 * @date 2019-09-12 16:46
 * @function
 */
public class MyTest {
    public static void main(String[] args) {
        Thread thread = new MyThread("non-daemon");
        Thread daemon = new MyDamon("daemon");
        daemon.setDaemon(true);
        //启动
        thread.start();
        daemon.start();
    }
}

class MyThread extends Thread{
    public MyThread(String name){
        super(name);
    }

    @Override
    public void run() {
        for(int i=0;i<5;i++){
            System.out.println(this.getName() +" "+i);
        }
    }
}
class MyDamon extends Thread{
    public MyDamon(String name){
        super(name);
    }

    @Override
    public void run() {
        for(int i=0;i<1000;i++){
            System.out.println(this.getName()+" "+i);
        }
    }
}