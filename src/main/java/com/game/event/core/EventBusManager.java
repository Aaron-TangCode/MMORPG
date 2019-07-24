package com.game.event.core;

import com.game.event.handler.IHandler;
import com.game.role.bean.ConcreteRole;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName EventBusManager
 * @Description TODO
 * @Author DELL
 * @Date 2019/7/24 18:19
 * @Version 1.0
 */
@Component
public class EventBusManager implements IEventBusManager {

    @Override
    public void synSubmit(IEvent event, ConcreteRole role) {

    }

    @Override
    public void asynSubmit(IEvent event, ConcreteRole role) {

    }

    /**
     * 调用方法
     * @param eventClass
     */
    public void invokeMethod(Class<? extends IEvent> eventClass,ConcreteRole role){
        List<IHandler> handlerList = role.getEventMap().get(eventClass);
        for (int i = 0; i < handlerList.size(); i++) {
            handlerList.get(i).exec();
        }
    }

    /**
     * 注册
     * @param eventClass
     * @param handlerList
     */
    public void register(Class<? extends IEvent> eventClass,List<IHandler> handlerList,ConcreteRole role){
        role.getEventMap().put(eventClass,handlerList);
    }
}
