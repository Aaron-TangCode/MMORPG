package com.test.observer;

/**
 * @ClassName Observer
 * @Description TODO
 * @Author DELL
 * @Date 2019/7/25 10:14
 * @Version 1.0
 */
public abstract class Observer {
    protected Subject subject;
    public abstract void update();
}
