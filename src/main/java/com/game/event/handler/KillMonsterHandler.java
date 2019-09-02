package com.game.event.handler;

import com.game.event.annotation.EventAnnotation;
import com.game.event.beanevent.MonsterDeadEvent;
import com.game.event.task.RecoveryMonsterTask;
import com.game.map.threadpool.TaskQueue;
import com.game.npc.bean.ConcreteMonster;
import com.game.role.bean.ConcreteRole;
import com.game.task.bean.RoleTask;
import com.game.task.service.TaskService;
import com.game.user.threadpool.UserThreadPool;
import com.game.utils.QuestType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName KillMonsterHandler
 * @Description 怪兽死亡处理器
 * @Author DELL
 * @Date 2019/7/24 18:33
 * @Version 1.0
 */
@EventAnnotation
@Component
public class KillMonsterHandler implements IHandler<MonsterDeadEvent> {

    /**
     * 处理器工具类
     */
    @Autowired
    private HandlerUtils handlerUtils;
    /**
     * 任务service
     */
    @Autowired
    private TaskService taskService;

    @EventAnnotation
    @Override
    public void exec(MonsterDeadEvent event) {
        //获取角色
        ConcreteRole role = event.getRole();
        //获取怪兽
        ConcreteMonster monster = event.getMonster();
        //怪兽复活
        recovery(monster,role);
        //获取count
        RoleTask roleTask = taskService.queryTask(role.getId());
        //没接任务
        if(roleTask==null){
            return;
        }
        Integer count = roleTask.getCount();
        //count自增
        count++;
        roleTask.setCount(count);
        //更新count
        taskService.updateTask(roleTask);
        //杀怪10次，完成任务
        if(count<=100){
            //处理任务
            handlerUtils.handleTask(event, QuestType.KM);
        }
    }

    /**
     * 复活怪兽任务
     * @param monster monster
     * @param role role
     */
    private void recovery(ConcreteMonster monster,ConcreteRole role) {
        //创建新的复活任务
        RecoveryMonsterTask recoveryMonsterTask = new RecoveryMonsterTask(monster,role);
        //任务队列
        role.setQueue(TaskQueue.getQueue());
        //任务添加到任务队列
        role.getQueue().add(recoveryMonsterTask);
        //执行任务
        UserThreadPool.executeTask(role,1,10L,10L, TimeUnit.SECONDS);
    }

}
