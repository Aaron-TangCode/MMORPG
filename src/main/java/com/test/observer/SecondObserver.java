package com.test.observer;

/**
 * @ClassName SecondObserver
 * @Description TODO
 * @Author DELL
 * @Date 2019/7/25 10:24
 * @Version 1.0
 */
public class SecondObserver extends Observer {
    public SecondObserver(Subject subject){
        this.subject = subject;
        this.subject.register(this);
    }
    @Override
    public void update() {
        System.out.println("secondObserver");
    }
}
