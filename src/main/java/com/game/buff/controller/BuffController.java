package com.game.buff.controller;

import com.game.buff.bean.ConcreteBuff;
import com.game.map.threadpool.TaskQueue;
import com.game.role.bean.ConcreteRole;
import com.game.server.manager.BuffMap;
import com.game.server.manager.TaskMap;
import com.game.user.threadpool.UserThreadPool;
import com.game.utils.MapUtils;
import io.netty.util.concurrent.Future;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import static com.game.buff.controller.BuffType.*;

/**
 * @ClassName BuffController
 * @Description buff控制器
 * @Author DELL
 * @Date 2019/6/20 16:59
 * @Version 1.0
 */
@Component
public class BuffController {
    /**
     * 执行buff
     * @param roleName 角色名
     * @param buffName Buff名
     */
    public void executeBuff(String roleName,String buffName){
        //获取角色
        ConcreteRole role = MapUtils.getMapRolename_Role().get(roleName);
        //获取本地buff
        Map<String, ConcreteBuff> buffMap = MapUtils.getBuffMap();
        ConcreteBuff buff = null;
        //选择Buff
        switch (buffName) {
            case RED :
                buff = buffMap.get("REDBUFF");
                break;
            case BLUE:
                buff = buffMap.get("BLUEBUFF");
                break;
            case DEFEND :
                buff = buffMap.get("DEFENDBUFF");
                break;
            case ATTACK :
                buff = buffMap.get("ATTACKBUFF");
                break;
                default:
                    buff = new ConcreteBuff();
                    break;
        }
        initBuffAndRole(role,buff,TaskQueue.getQueue(),TaskMap.getFutureMap(), BuffMap.getBuffMap());
        //创建任务
        BuffTask task = new BuffTask(buff,role);
        //把任务加到任务队列
        role.getQueue().add(task);
        //添加buff
        role.getMapBuff().put(buffName,buff);
        role.setBuff(buff);
        //设置名字
        buff.setName(buffName);
        //把任务丢线程池
        int threadIndex = UserThreadPool.getThreadIndex(role.getId());
        Future future =  UserThreadPool.executeTask(role,threadIndex, 5L, 5L, TimeUnit.SECONDS);

        //存在map中
        role.getTaskMap().put(String.valueOf(role.getId()),future);


    }

    /**
     * 初始化buff和role
     * @param role 角色
     * @param buff buff
     * @param queue 容器队列
     * @param map 容器map
     * @param buffMap 容器buffMap
     */
    public void initBuffAndRole(ConcreteRole role, ConcreteBuff buff, Queue<Runnable> queue,Map<String,Future> map,Map<String,ConcreteBuff> buffMap){
        role.setMapBuff(buffMap);
        buff.setRole(role);
        role.setQueue(queue);
        role.setTaskMap(map);
    }
}
