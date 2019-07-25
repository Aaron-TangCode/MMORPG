package com.test.observer;

/**
 * @ClassName FirstObserver
 * @Description TODO
 * @Author DELL
 * @Date 2019/7/25 10:24
 * @Version 1.0
 */
public class FirstObserver extends Observer {
    public FirstObserver(Subject subject){
        this.subject = subject;
        this.subject.register(this);
    }
    @Override
    public void update() {
        System.out.println("FirstObserver");
    }
}
