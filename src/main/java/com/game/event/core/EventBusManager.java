package com.game.event.core;

import com.game.event.handler.IHandler;
import com.game.role.bean.ConcreteRole;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName EventBusManager
 * @Description 事件管理器
 * @Author DELL
 * @Date 2019/7/24 18:19
 * @Version 1.0
 */
@Component
public class EventBusManager implements IEventBusManager {
    /**
     * 事件map
     */
    private static Map<Class<? extends IEvent>, List<IHandler>> eventMap = new HashMap<>();

    /**
     * 事件容器map
     * @return
     */
    public static Map<Class<? extends IEvent>, List<IHandler>> getEventMap(){
        return eventMap;
    }
    @Override
    public void synSubmit(IEvent event, ConcreteRole role) {

    }

    @Override
    public void asynSubmit(IEvent event, ConcreteRole role) {

    }
}
