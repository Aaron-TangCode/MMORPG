package com.test.eventbus;

import com.google.common.eventbus.Subscribe;

/**
 * @ClassName DataObserver1
 * @Description TODO
 * @Author DELL
 * @Date 2019/7/22 14:28
 * @Version 1.0
 */
public class DataObserver1 {
    @Subscribe
    public void func(String msg){
        System.out.println("string msg:"+msg);
    }

    @Subscribe
    public void func2(String msg){
        System.out.println("string msg2:"+msg);
    }
}
