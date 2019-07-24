package com.test.eventbus;

/**
 * @ClassName Test
 * @Description TODO
 * @Author DELL
 * @Date 2019/7/22 14:30
 * @Version 1.0
 */
public class Test {
    public static void main(String[] args) {
        DataObserver1 dataObserver1 = new DataObserver1();
        DateObserver2 dateObserver2 = new DateObserver2();
        //注册
        EventBusCenter.register(dataObserver1);
        EventBusCenter.register(dateObserver2);

        System.out.println("=====start======");

        EventBusCenter.post("post string method");
        EventBusCenter.post(123);

        EventBusCenter.unregister(dateObserver2);
        EventBusCenter.post("post string method");
        EventBusCenter.post(123);

        System.out.println("=====end======");
    }
}
