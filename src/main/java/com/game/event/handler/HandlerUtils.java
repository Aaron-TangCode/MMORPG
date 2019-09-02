package com.game.event.handler;

import com.game.event.core.IEvent;
import com.game.protobuf.protoc.MsgSkillInfoProto;
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
 * @ClassName HandlerUtils
 * @Description 任务攻击类
 * @Author DELL
 * @Date 2019/8/27 21:30
 * @Version 1.0
 */
@Component
public class HandlerUtils {
    /**
     * 角色service
     */
    @Autowired
    private RoleService roleService;
    /**
     * 任务service
     */
    @Autowired
    private TaskService taskService;

    public void handleTask(IEvent event, QuestType questType){
        //获取角色
        ConcreteRole role = event.getRole();
        //从已接受任务中找具体任务
        Map<Integer, ConcreteTask> receivedTaskMap = role.getReceivedTaskMap();
        //获取count
        RoleTask roleTask = taskService.queryTask(role.getId());
        //准备一个容器装任务
        List<ConcreteTask> taskList = new ArrayList<>();
        if(receivedTaskMap==null){
            return;
        }
        //遍历任务
        for (Map.Entry<Integer, ConcreteTask> taskEntry : receivedTaskMap.entrySet()) {
            ConcreteTask value = taskEntry.getValue();
            if(questType.equals(value.getQuestType())){
                taskList.add(value);
            }
        }
        String content = "--";
        //遍历处理任务
        for (ConcreteTask task : taskList) {
            //完成任务
            String finishedTask = roleTask.getFinishedTask();
            StringBuffer sb = new StringBuffer();
            if(finishedTask!=null&&!(finishedTask.equals(""))){
                sb.append(finishedTask);
                sb.append(",");
            }
            sb.append(task.getId());
            roleTask.setFinishedTask(sb.toString());
            //更新任务
            taskService.updateTask(roleTask);
            //移除任务
            String receivedTask = roleTask.getReceivedTask();
            String[] words = receivedTask.split(",");
            StringBuffer newSb = new StringBuffer();
            String taskId = task.getId().toString();
            for (int i = 0; i < words.length; i++) {
                if(!words[i].equals(taskId)&&i<words.length){
                    newSb.append(words[i]);
                }
                if(i!=words.length-2&&i<words.length-1){
                    newSb.append(",");
                }
            }
            roleTask.setReceivedTask(newSb.toString());
            taskService.updateTask(roleTask);
            //任务奖励
            ConcreteTask localTask = TaskMap.getTaskMap().get(task.getId());
            Integer bonus = localTask.getBonus();
            //获取金币
            role.setMoney(role.getMoney()+bonus);
            //更新数据
            roleService.updateRole(role);
            content = "恭喜完成任务，领取任务奖励";
        }
        MsgSkillInfoProto.ResponseSkillInfo skillInfo = MsgSkillInfoProto.ResponseSkillInfo.newBuilder()
                .setContent(content)
                .setType(MsgSkillInfoProto.RequestType.UPGRADESKILL)
                .build();
        role.getChannel().writeAndFlush(skillInfo);
    }

}
