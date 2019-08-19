package com.game.event.core;

import com.game.annotation.ExcelAnnotation;
import com.game.event.beanevent.AttackedEvent;
import com.game.event.beanevent.MonsterDeadEvent;
import com.game.event.handler.AttackedHandler;
import com.game.event.handler.IHandler;
import com.game.event.handler.KillMonsterHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName InjectEventHandler
 * @Description 注入事件处理器
 * @Author DELL
 * @Date 2019/7/25 22:03
 * @Version 1.0
 */
@ExcelAnnotation
public class InjectEventHandler {
    /**
     * 注入数据
     */
    @ExcelAnnotation
    public void injectData(){
        Map<Class<? extends IEvent>, List<IHandler>> eventMap = EventBusManager.getEventMap();

        List<IHandler> handlerList = new ArrayList<>();
        //添加处理器
        handlerList.add(new KillMonsterHandler());
        handlerList.add(new AttackedHandler());
        //把事件和handler放一起
        eventMap.put(MonsterDeadEvent.class,handlerList);
        eventMap.put(AttackedEvent.class,handlerList);
    }
}
