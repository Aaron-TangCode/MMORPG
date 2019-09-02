package com.game.event.handler;

import com.game.event.annotation.EventAnnotation;
import com.game.event.beanevent.TalkEvent;
import com.game.utils.QuestType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName TalkHandler
 * @Description 谈话处理器
 * @Author DELL
 * @Date 2019/8/27 20:41
 * @Version 1.0
 */
@EventAnnotation
@Component
public class TalkHandler implements IHandler<TalkEvent> {
    /**
     * 处理器工具类
     */
    @Autowired
    private HandlerUtils handlerUtils;

    @EventAnnotation
    @Override
    public void exec(TalkEvent talkEvent) {
        //处理任务
        handlerUtils.handleTask(talkEvent, QuestType.TALK);
    }
}
