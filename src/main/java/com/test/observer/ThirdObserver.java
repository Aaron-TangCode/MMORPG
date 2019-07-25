package com.test.observer;

/**
 * @ClassName ThirdObserver
 * @Description TODO
 * @Author DELL
 * @Date 2019/7/25 10:27
 * @Version 1.0
 */
public class ThirdObserver extends Observer {
    public ThirdObserver(Subject subject){
        this.subject = subject;
        this.subject.register(this);
    }
    @Override
    public void update() {
        System.out.println("thirdObserver");
    }
}
