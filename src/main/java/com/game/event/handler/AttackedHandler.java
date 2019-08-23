package com.game.event.handler;

import com.game.event.annotation.EventAnnotation;
import com.game.event.beanevent.AttackedEvent;
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
    @EventAnnotation
    @Override
    public void exec(AttackedEvent attackedEvent) {
        //取消buff
        CancleBuff.cancleBuffMethod(attackedEvent);
    }
}
