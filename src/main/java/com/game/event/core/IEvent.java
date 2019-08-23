package com.game.event.core;

import com.game.role.bean.ConcreteRole;
import org.springframework.stereotype.Component;

/**
 * @ClassName IEvent
 * @Description 事件标记类
 * @Author DELL
 * @Date 2019/7/23 10:53
 * @Version 1.0
 */
@Component
public abstract class IEvent {
    public abstract ConcreteRole getRole();
}
