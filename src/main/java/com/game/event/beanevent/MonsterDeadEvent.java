package com.game.event.beanevent;

import com.game.event.annotation.EventAnnotation;
import com.game.event.core.IEvent;
import com.game.npc.bean.ConcreteMonster;
import com.game.role.bean.ConcreteRole;
import org.springframework.stereotype.Component;

/**
 * @ClassName MonsterDeadEvent
 * @Description 怪兽死亡事件
 * @Author DELL
 * @Date 2019/7/23 11:45
 * @Version 1.0
 */
@EventAnnotation
@Component
public class MonsterDeadEvent extends IEvent {
    private ConcreteRole role;
    private ConcreteMonster monster;

    public ConcreteRole getRole() {
        return role;
    }

    public void setRole(ConcreteRole role) {
        this.role = role;
    }

    public ConcreteMonster getMonster() {
        return monster;
    }

    public void setMonster(ConcreteMonster monster) {
        this.monster = monster;
    }
}
