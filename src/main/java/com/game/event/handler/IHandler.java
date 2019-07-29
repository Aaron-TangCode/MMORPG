package com.game.event.handler;

import com.game.event.core.IEvent;
import org.springframework.stereotype.Component;

/**
 * @ClassName IHandler
 * @Description TODO
 * @Author DELL
 * @Date 2019/7/24 18:31
 * @Version 1.0
 */
@Component
public  interface IHandler<E extends IEvent> {

    public abstract void exec(E e);

}
