package com.game.event.beanevent;

import com.game.event.annotation.EventAnnotation;
import com.game.event.core.IEvent;
import com.game.role.bean.ConcreteRole;
import org.springframework.stereotype.Component;

/**
 * @ClassName GoVillageHandler
 * @Description 回村子事件
 * @Author DELL
 * @Date 2019/8/22 10:31
 * @Version 1.0
 */
@Component
@EventAnnotation
public class GoVillageEvent extends IEvent {
    /**
     * 角色
     */
    private ConcreteRole role;
    @Override
    public ConcreteRole getRole() {
        return role;
    }

    public void setRole(ConcreteRole role) {
        this.role = role;
    }
}
