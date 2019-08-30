package com.game.event.handler;

import com.game.event.annotation.EventAnnotation;
import com.game.event.beanevent.GoodsEvent;
import com.game.utils.QuestType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName GoodsHandler
 * @Description 物品处理器
 * @Author DELL
 * @Date 2019/8/27 20:45
 * @Version 1.0
 */
@EventAnnotation
@Component
public class UseGoodsHandler implements IHandler<GoodsEvent> {
    @Autowired
    private HandlerUtils handlerUtils;
    @EventAnnotation
    @Override
    public void exec(GoodsEvent goodsEvent) {
        //处理任务
        handlerUtils.handleTask(goodsEvent, QuestType.USEGOODS);
    }
}
