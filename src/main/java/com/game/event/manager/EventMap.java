package com.game.event.manager;

import com.game.event.core.IEvent;
import com.game.event.handler.IHandler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName EventMap
 * @Description 事件和处理器的容器
 * @Author DELL
 * @Date 2019/7/29 9:28
 * @Version 1.0
 */
@Component
public class EventMap {
    /**
     * 事件容器map
     */
    private static volatile Map<Class<? extends IEvent>, List<IHandler>> eventMap = new HashMap<>();


    private EventMap(){}


    public static Map<Class<? extends IEvent>, List<IHandler>> getEventMap(){
        return eventMap;
    }

    /**
     * 处理任务事件逻辑
     * @param event
     */
    public void submit(IEvent event){
        //获取class
        Class<? extends IEvent> clazz = event.getClass();
        //触发Handler
        List<IHandler> handlerList = EventMap.getEventMap().get(clazz);
        //遍历执行
        for (int i = 0; i < handlerList.size(); i++) {
            handlerList.get(i).exec(event);
        }

    }
}
