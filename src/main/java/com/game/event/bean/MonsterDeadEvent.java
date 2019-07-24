package com.game.event.bean;

import com.game.event.core.EventBusManager;
import com.game.event.core.IEvent;
import com.game.event.service.EventService;
import com.game.role.bean.ConcreteRole;
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
    private EventService eventService;

    @Autowired
    private EventBusManager eventBusManager;

    public void onfire(ConcreteRole role) {
        int count = eventService.queryCount(role.getId());
        //自增
        count++;
        //更新到数据库
        eventService.updateCount(count,role.getId());
        //事件触发handler
        if(count==1){
            eventBusManager.invokeMethod(MonsterDeadEvent.class,role);
        }else if(count==10){
            eventBusManager.invokeMethod(MonsterDeadEvent.class,role);
        }
    }
}
