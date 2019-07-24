package com.game.event.core;

import com.game.role.bean.ConcreteRole;

/**
 * @ClassName IEventBusManager
 * @Description TODO
 * @Author DELL
 * @Date 2019/7/24 18:18
 * @Version 1.0
 */
public interface IEventBusManager {
    /**
     * 同步
     * @param event
     * @param role
     */
    public void synSubmit(IEvent event, ConcreteRole role);

    /**
     * 异步
     * @param event
     * @param role
     */
    public void asynSubmit(IEvent event,ConcreteRole role);
}
