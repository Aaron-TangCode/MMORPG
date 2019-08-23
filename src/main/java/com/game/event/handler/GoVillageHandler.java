package com.game.event.handler;

import com.game.event.annotation.EventAnnotation;
import com.game.event.beanevent.GoVillageEvent;
import org.springframework.stereotype.Component;

/**
 * @ClassName GoVillageHandler
 * @Description 回村事件
 * @Author DELL
 * @Date 2019/8/22 10:32
 * @Version 1.0
 */
@EventAnnotation
@Component
public class GoVillageHandler implements IHandler<GoVillageEvent> {
    @EventAnnotation
    @Override
    public void exec(GoVillageEvent goVillageEvent) {
        //取消buff
        CancleBuff.cancleBuffMethod(goVillageEvent);
    }
}
