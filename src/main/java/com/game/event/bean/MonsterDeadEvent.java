package com.game.event.bean;

import com.game.event.core.EventBusManager;
import com.game.event.core.IEvent;
import com.game.npc.bean.ConcreteMonster;
import com.game.role.bean.ConcreteRole;
import com.game.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName MonsterDeadEvent
 * @Description 怪兽死亡事件
 * @Author DELL
 * @Date 2019/7/23 11:45
 * @Version 1.0
 */
@Component
public class MonsterDeadEvent implements IEvent {

    @Autowired
    private EventBusManager eventBusManager;

    @Autowired
    private TaskService taskService;

    public void onfire(ConcreteRole role, ConcreteMonster monster) {
        taskService.onMonsterBeKiied(role,monster);
    }
}
