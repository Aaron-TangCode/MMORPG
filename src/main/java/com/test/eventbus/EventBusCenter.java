package com.test.eventbus;

import com.google.common.eventbus.EventBus;

/**
 * @ClassName EventBusCenter
 * @Description TODO
 * @Author DELL
 * @Date 2019/7/22 14:24
 * @Version 1.0
 */
public class EventBusCenter {
    private static EventBus eventBus = new EventBus();

    private EventBusCenter(){}

    public static EventBus getEventBus(){
        return eventBus;
    }

    public static void register(Object obj){
        eventBus.register(obj);
    }

    public static void unregister(Object obj){
        eventBus.unregister(obj);
    }

    public static void post(Object obj){
        eventBus.post(obj);
    }
}
