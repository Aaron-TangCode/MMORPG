package com.game.event.manager;

import com.game.event.core.IEvent;
import com.game.task.bean.ConcreteTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName EventTaskMap
 * @Description 事件和任务的容器
 * @Author DELL
 * @Date 2019/7/29 16:41
 * @Version 1.0
 */
public class EventTaskMap {
    /**
     * 事件和任务的容器map
     */
    private static volatile Map<Class<? extends IEvent>, List<ConcreteTask>> eventTaskMap = new HashMap<>();
    private EventTaskMap(){}

    public static  Map<Class<? extends IEvent>, List<ConcreteTask>> getEventTaskMap(){
        return eventTaskMap;
    }

    public void register(IEvent event) {
        Class<? extends IEvent> aClass = event.getClass();
    }
}
