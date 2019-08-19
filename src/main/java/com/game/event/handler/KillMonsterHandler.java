package com.game.event.handler;

import com.game.event.annotation.EventAnnotation;
import com.game.event.beanevent.MonsterDeadEvent;
import com.game.npc.bean.ConcreteMonster;
import com.game.role.bean.ConcreteRole;
import com.game.role.service.RoleService;
import com.game.task.bean.ConcreteTask;
import com.game.task.bean.RoleTask;
import com.game.task.manager.TaskMap;
import com.game.task.service.TaskService;
import com.game.utils.QuestType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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


    @Autowired
    private RoleService roleService;
    @Autowired
    private TaskService taskService;

    @EventAnnotation
    @Override
    public void exec(MonsterDeadEvent event) {
        //获取角色
        ConcreteRole role = event.getRole();
        //获取怪兽
        ConcreteMonster monster = event.getMonster();
        //获取count
        RoleTask roleTask = taskService.queryTask(role.getId());
        Integer count = roleTask.getCount();
        //count自增
        count++;
        roleTask.setCount(count);
        //更新count
        taskService.updateTask(roleTask);



        //杀怪10次，完成任务
        if(count<=100){
            //从已接受任务中找具体任务
            Map<Integer, ConcreteTask> receivedTaskMap = role.getReceivedTaskMap();

            //Q:怎么根据时间来找具体任务
            List<ConcreteTask> taskList = new ArrayList<>();
            for (Map.Entry<Integer, ConcreteTask> taskEntry : receivedTaskMap.entrySet()) {
                ConcreteTask value = taskEntry.getValue();
                if(QuestType.KM.equals(value.getQuestType())){
                    taskList.add(value);
                }
            }

            for (ConcreteTask task : taskList) {
                //完成任务
                String finishedTask = roleTask.getFinishedTask();
                StringBuffer sb = new StringBuffer(finishedTask);
                sb.append(",").append(task.getId());
                roleTask.setFinishedTask(sb.toString());
                //更新任务
                taskService.updateTask(roleTask);
                //移除任务
                String receivedTask = roleTask.getReceivedTask();
                String[] words = receivedTask.split(",");
                StringBuffer newSb = new StringBuffer();
                String taskId = task.getId().toString();
                for (int i = 0; i < words.length; i++) {
                    if(!words[i].equals(taskId)&&i!=words.length-1){
                        newSb.append(words[i]+",");
                    }
                    if(!words[i].equals(taskId)&&i==words.length-1){
                        newSb.append(words[i]);
                    }
                }
                roleTask.setReceivedTask(newSb.toString());
                taskService.updateTask(roleTask);
                //任务奖励
                ConcreteTask localTask = TaskMap.getTaskMap().get(task.getId());
                Integer bonus = localTask.getBonus();

                role.setMoney(role.getMoney()+bonus);

                //更新数据
                roleService.updateRole(role);
            }


            role.getChannel().writeAndFlush("恭喜完成任务，领取任务奖励");
        }
    }

}
