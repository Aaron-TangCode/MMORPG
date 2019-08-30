package com.game.event.core;

import com.game.annotation.ExcelAnnotation;
import com.game.event.beanevent.*;
import com.game.event.handler.*;

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
        handlerList.add(new GoVillageHandler());
        handlerList.add(new UseGoodsHandler());
        handlerList.add(new TalkHandler());
        handlerList.add(new Trade2Handler());

        //把事件和handler放一起
        eventMap.put(MonsterDeadEvent.class,handlerList);
        eventMap.put(AttackedEvent.class,handlerList);
        eventMap.put(GoVillageEvent.class,handlerList);
        eventMap.put(GoodsEvent.class,handlerList);
        eventMap.put(TalkEvent.class,handlerList);
        eventMap.put(TradeEvent.class,handlerList);
    }
}
