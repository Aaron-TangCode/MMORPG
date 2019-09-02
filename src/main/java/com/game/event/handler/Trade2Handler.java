package com.game.event.handler;

import com.game.event.annotation.EventAnnotation;
import com.game.event.beanevent.TradeEvent;
import com.game.utils.QuestType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName TradeHandler
 * @Description 交易处理器
 * @Author DELL
 * @Date 2019/8/27 20:44
 * @Version 1.0
 */
@EventAnnotation
@Component
public class Trade2Handler implements IHandler<TradeEvent>{
    /**
     * 处理器工具类
     */
    @Autowired
    private HandlerUtils handlerUtils;
    @EventAnnotation
    @Override
    public void exec(TradeEvent tradeEvent) {
        //处理任务
        handlerUtils.handleTask(tradeEvent, QuestType.TRADE);
    }
}
