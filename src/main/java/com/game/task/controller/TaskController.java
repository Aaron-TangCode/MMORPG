package com.game.task.controller;

import com.game.dispatcher.RequestAnnotation;
import com.game.event.bean.MonsterDeadEvent;
import com.game.event.core.EventBusManager;
import com.game.event.handler.IHandler;
import com.game.event.handler.KillMonsterHandler;
import com.game.event.service.EventService;
import com.game.role.bean.ConcreteRole;
import com.game.task.bean.ConcreteTask;
import com.game.task.bean.RoleTask;
import com.game.task.manager.TaskMap;
import com.game.task.service.TaskService;
import com.game.utils.MapUtils;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TaskController
 * @Description TODO
 * @Author DELL
 * @Date 2019/7/23 15:19
 * @Version 1.0
 */
@RequestAnnotation("/task")
@Controller
public class TaskController {
    @Autowired
    private EventService eventService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private EventBusManager eventBusManager;
    /**
     * 查询可接受的任务
     */
    @RequestAnnotation("/queryReceivableTask")
    public void queryReceivableTask(String roleName){
        //获取角色
        ConcreteRole role = getRole(roleName);

        Map<Integer, ConcreteTask> receivableTaskMap = handleReceivableData(role);
        //打印map
        printMap(receivableTaskMap,role);
    }

    private Map<Integer, ConcreteTask> handleReceivableData(ConcreteRole role) {
        //查询数据库
        RoleTask roleTask = taskService.queryTask(role.getId());
        //解析数据
        String finishedTask = roleTask.getFinishedTask();
        String receivedTask = roleTask.getReceivedTask();
        //本地缓存
        Map<Integer, ConcreteTask> taskMap = TaskMap.getTaskMap();
        Map<Integer, ConcreteTask> tmpTaskMap = new HashMap<>(taskMap);
        //清掉原来缓存数据
        role.getReceivableTaskMap().clear();
        role.getReceivedTaskMap().clear();
        role.getFinishedTaskMap().clear();
        //解析数据
        String[] split = null;
        String[] split1 = null;
        //解析已完成的任务
        if(finishedTask!=null){
            split = finishedTask.split(",");
            for (int i = 0; i < split.length; i++) {
                ConcreteTask tmpTask = tmpTaskMap.remove(Integer.parseInt(split[i]));
                role.getFinishedTaskMap().put(tmpTask.getId(),tmpTask);
            }
        }
        //解析已接受的任务
        if(receivedTask!=null){
            split1 = receivedTask.split(",");
            for (int i = 0; i < split1.length; i++) {
                ConcreteTask tmpTask = tmpTaskMap.remove(Integer.parseInt(split1[i]));
                role.getReceivedTaskMap().put(tmpTask.getId(),tmpTask);
            }
        }
        Map<Integer, ConcreteTask> receivableTaskMap = role.getReceivableTaskMap();
        receivableTaskMap = tmpTaskMap;
        return receivableTaskMap;
    }

    /**
     * 查询已接受的任务
     */
    @RequestAnnotation("/queryReceivedTask")
    public void queryReceivedTask(String roleName){
        ConcreteRole role = getRole(roleName);
        //查询数据库
        Map<Integer, ConcreteTask> receivedTaskMap = handleReceivedData(role);
        //打印消息
        printMap(receivedTaskMap,role);

    }

    private Map<Integer, ConcreteTask> handleReceivedData(ConcreteRole role) {
        //查询数据库
        RoleTask roleTask = taskService.queryTask(role.getId());
        //解析数据
        String receivedTask = roleTask.getReceivedTask();
        //本地缓存
        Map<Integer, ConcreteTask> taskMap = TaskMap.getTaskMap();
        Map<Integer, ConcreteTask> tmpTaskMap = new HashMap<>(taskMap);
        //清掉原来缓存数据
        role.getReceivedTaskMap().clear();
        //解析数据
        String[] split1 = null;
        //解析已接受的任务
        if(receivedTask!=null){
            split1 = receivedTask.split(",");
            for (int i = 0; i < split1.length; i++) {
                ConcreteTask tmpTask = tmpTaskMap.remove(Integer.parseInt(split1[i]));
                role.getReceivedTaskMap().put(tmpTask.getId(),tmpTask);
            }
        }
        return role.getReceivedTaskMap();
    }

    /**
     * 接受任务
     * @param roleName
     * @param taskId
     */
    @RequestAnnotation("/receiveTask")
    public void receiveTask(String roleName,String taskId){
        //获取角色
        ConcreteRole role = getRole(roleName);
        //查询
        RoleTask roleTask = taskService.queryTask(role.getId());
        String receivedTask = roleTask.getReceivedTask();
        StringBuffer sb = new StringBuffer(receivedTask);
        if(receivedTask==null){
            sb.append(taskId);
        }else{
            sb.append(",").append(taskId);
        }
        roleTask.setReceivedTask(sb.toString());
        //更新数据库信息
        taskService.updateTask(roleTask);
        //注册事件
        List<IHandler> list = new ArrayList<>();
        list.add(new KillMonsterHandler());
        //todo 这里需要做一个扩展：任务的自动匹配
        eventBusManager.register(MonsterDeadEvent.class,list,role);
        //增加数据到roleCount中
        eventService.insert(role.getId(),0);
        role.getCtx().channel().writeAndFlush("成功接受任务");
    }

    /**
     * 放弃任务
     * @param roleName
     * @param taskId
     */
    @RequestAnnotation("/discardTask")
    public void discardTask(String roleName,String taskId){
        //获取角色
        ConcreteRole role = getRole(roleName);
        //更新数据
        updateData(role,taskId);
        role.getCtx().channel().writeAndFlush("成功放弃任务");
    }

    private void updateData(ConcreteRole role,String taskId) {
        //获取任务数据
        RoleTask roleTask = taskService.queryTask(role.getId());
        String receivedTask = roleTask.getReceivedTask();

        if(receivedTask==null){
            role.getCtx().channel().writeAndFlush("没已接受的任务");
            return;
        }
        //[1,2]
        String[] task = receivedTask.split(",");
        StringBuffer newTask = new StringBuffer();

        for (int i = 0; i < task.length; i++) {
            if (!task[i].equals(taskId)&&i!=task.length-1) {
                newTask.append(task[i]+",");
            }
            if (!task[i].equals(taskId)) {
                newTask.append(task[i]);
            }
        }

        roleTask.setReceivedTask(newTask.toString());
        //更新数据库信息
        taskService.updateTask(roleTask);
    }

    /**
     * 获取角色
     * @param roleName
     * @return
     */
    public ConcreteRole getRole(String roleName){
        return MapUtils.getMapRolename_Role().get(roleName);
    }

    /**
     * 打印map
     * @param map
     */
    private void printMap(Map<Integer, ConcreteTask> map,ConcreteRole role){
        String outputContent = "任务id:{0}\t任务描述:{1}\t任务奖励:{2}\t完成条件:{3}";
        Channel channel = role.getCtx().channel();
        for (Map.Entry<Integer, ConcreteTask> taskEntry : map.entrySet()) {
            channel.writeAndFlush(MessageFormat.format(outputContent, taskEntry.getKey(), taskEntry.getValue().getTaskDescription(),
                    taskEntry.getValue().getBonus(), taskEntry.getValue().getCondition())) ;
        }
    }
}
