package com.game.event.handler;

import com.game.event.core.IEvent;
import com.game.role.bean.ConcreteRole;
import io.netty.util.concurrent.Future;

/**
 * @ClassName CancleBuff
 * @Description 取消buff
 * @Author DELL
 * @Date 2019/8/22 10:38
 * @Version 1.0
 */
public class CancleBuff {
    /**
     * 取消Buff
     * @param event event
     */
    public static void cancleBuffMethod(IEvent event){
        ConcreteRole role = event.getRole();
        //根据TaskMap和TaskQueue找到对应的buff
        if(role.getTaskMap()==null){
            return;
        }
        Future future = role.getTaskMap().get(String.valueOf(role.getId()));
        //除去buff
//        role.getMapBuff().remove(role.getBuff().getName());
        //取消buff
        if(future!=null){
            future.cancel(true);
        }

    }
}
