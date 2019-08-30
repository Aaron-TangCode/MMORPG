package com.game.event.beanevent;

import com.game.event.core.IEvent;
import com.game.role.bean.ConcreteRole;
import org.springframework.stereotype.Component;

/**
 * @ClassName TalkEvent
 * @Description 谈话事件
 * @Author DELL
 * @Date 2019/8/27 20:39
 * @Version 1.0
 */
@Component
public class TalkEvent extends IEvent {
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
