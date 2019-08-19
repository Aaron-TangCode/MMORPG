package com.game.event.handler;

import com.game.event.annotation.EventAnnotation;
import com.game.event.beanevent.AttackedEvent;
import com.game.role.bean.ConcreteRole;
import io.netty.util.concurrent.Future;
import org.springframework.stereotype.Component;

/**
 * @ClassName AttackedHandler
 * @Description 被攻击处理器
 * @Author DELL
 * @Date 2019/8/14 20:27
 * @Version 1.0
 */
@EventAnnotation
@Component
public class AttackedHandler implements IHandler<AttackedEvent> {
    /**
     * 执行处理器
     * @param attackedEvent 被攻击事件
     */
    @Override
    public void exec(AttackedEvent attackedEvent) {
        ConcreteRole role = attackedEvent.getRole();
        //根据TaskMap和TaskQueue找到对应的buff
        Future future = role.getTaskMap().get(String.valueOf(role.getId()));
        //除去buff
        role.getMapBuff().remove(role.getBuff().getName());
        //取消buff
        future.cancel(true);
    }
}
