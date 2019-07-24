package com.test.eventbus;

import com.google.common.eventbus.Subscribe;

/**
 * @ClassName DateObserver2
 * @Description TODO
 * @Author DELL
 * @Date 2019/7/22 14:29
 * @Version 1.0
 */
public class DateObserver2 {
    @Subscribe
    public void func(Integer msg){
        System.out.println("Integer msg:"+msg);
    }
}
