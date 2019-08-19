package com.game.event.handler;

import com.game.event.annotation.EventAnnotation;
import com.game.event.beanevent.NPCEvent;
import org.springframework.stereotype.Component;

/**
 * @ClassName NPCHandler
 * @Description NPC处理器
 * @Author DELL
 * @Date 2019/7/29 9:46
 * @Version 1.0
 */
@Component
@EventAnnotation
public class NPCHandler implements IHandler<NPCEvent> {


    @EventAnnotation
    @Override
    public void exec(NPCEvent npcEvent) {

    }
}
