package com.game.event.beanevent;

import com.game.event.core.IEvent;
import com.game.role.bean.ConcreteRole;
import org.springframework.stereotype.Component;

/**
 * @ClassName TradeEvent
 * @Description 交易事件
 * @Author DELL
 * @Date 2019/8/27 20:42
 * @Version 1.0
 */
@Component
public class TradeEvent extends IEvent {
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
