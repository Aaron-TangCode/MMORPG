package com.game.task.service;

import com.game.event.bean.MonsterDeadEvent;
import com.game.event.core.EventBusManager;
import com.game.event.handler.IHandler;
import com.game.npc.bean.ConcreteMonster;
import com.game.role.bean.ConcreteRole;
import com.game.task.bean.RoleTask;
import com.game.task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName TaskService
 * @Description TODO
 * @Author DELL
 * @Date 2019/7/22 17:37
 * @Version 1.0
 */
@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;


    public RoleTask queryTask(int id) {
        return taskRepository.queryTask(id);
    }

    public void updateTask(RoleTask roleTask) {
        taskRepository.updateTask(roleTask);
    }

    /**
     * 处理任务事件逻辑
     * @param role
     * @param monster
     */
    public void onMonsterBeKiied(ConcreteRole role, ConcreteMonster monster) {
       //查询count
        RoleTask roleTask = taskRepository.queryTask(role.getId());
        int count = roleTask.getCount();
        //count自增
        count++;
        roleTask.setCount(count);
        //更新数据
        taskRepository.updateTask(roleTask);
        //todo:做任务的自动适配事件
        //触发Handler
        if(count<10){
            List<IHandler> handlerList = EventBusManager.getEventMap().get(MonsterDeadEvent.class);
            for (int i = 0; i < handlerList.size(); i++) {
                handlerList.get(i).exec();
            }
        }
    }
}
