package com.game.buff.controller;

import com.game.buff.bean.ConcreteBuff;
import com.game.role.bean.ConcreteRole;
import com.game.user.threadpool.UserThreadPool;
import com.game.utils.MapUtils;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName BuffController
 * @Description TODO
 * @Author DELL
 * @Date 2019/6/20 16:59
 * @Version 1.0
 */
@Component
public class BuffController {
    /**
     * 执行Buff
     */
    public void executeBuff(String roleName){
        //获取角色
        ConcreteRole role = MapUtils.getMapRolename_Role().get(roleName);
        //获取channelhandlercontext
        ChannelHandlerContext ctx = role.getCtx();
        Map<Integer, ConcreteBuff> buffMap = MapUtils.getBuffMap();
        Set<Map.Entry<Integer, ConcreteBuff>> entries = buffMap.entrySet();
        Iterator<Map.Entry<Integer, ConcreteBuff>> iterator = entries.iterator();

        int id = Math.abs(ctx.channel().id().hashCode());
        int modIndex = id% UserThreadPool.DEFAULT_THREAD_POOL_SIZE;

        while(iterator.hasNext()){
            Map.Entry<Integer, ConcreteBuff> next = iterator.next();
            ConcreteBuff buff = next.getValue();
            //获取对象
            //客户端的唯一标识

            Task task = new Task(buff,role);

            UserThreadPool.ACCOUNT_SERVICE[modIndex].scheduleAtFixedRate(task,5,buff.getPeriod(), TimeUnit.SECONDS);
        }
    }
}
