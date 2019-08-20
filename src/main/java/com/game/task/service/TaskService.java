package com.game.task.service;

import com.game.protobuf.protoc.MsgTaskInfoProto;
import com.game.role.bean.ConcreteRole;
import com.game.task.bean.ConcreteTask;
import com.game.task.bean.RoleTask;
import com.game.task.manager.TaskMap;
import com.game.task.repository.TaskRepository;
import com.game.user.manager.LocalUserMap;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName TaskService
 * @Description 任务服务
 * @Author DELL
 * @Date 2019/7/22 17:37
 * @Version 1.0
 */
@Service
public class TaskService {
    /**
     * 任务数据访问
     */
    @Autowired
    private TaskRepository taskRepository;

    /**
     * 查询任务
     * @param id id
     * @return 角色任务
     */
    public RoleTask queryTask(int id) {
        return taskRepository.queryTask(id);
    }

    /**
     * 更新任务
     * @param roleTask 角色任务
     */
    public void updateTask(RoleTask roleTask) {
        taskRepository.updateTask(roleTask);
    }

    /**
     * 查询可接受任务
     * @param channel channel
     * @param requestTaskInfo 任务请求信息
     * @return 协议信息
     */
    public MsgTaskInfoProto.ResponseTaskInfo queryReceivableTask(Channel channel, MsgTaskInfoProto.RequestTaskInfo requestTaskInfo) {
        //获取角色
        ConcreteRole role = getRole(channel);

        Map<Integer, ConcreteTask> receivableTaskMap = handleReceivableData(role);
        //打印map
        String content = printMap(receivableTaskMap,role);
        //返回消息
        return MsgTaskInfoProto.ResponseTaskInfo.newBuilder()
                .setContent(content)
                .setType(MsgTaskInfoProto.RequestType.QUERYRECEIVABLETASK)
                .build();
    }
    /**
     * 打印map
     * @param map map
     */
    private String printMap(Map<Integer, ConcreteTask> map,ConcreteRole role){
        String outputContent = "任务id:{0}\t任务描述:{1}\t任务奖励:{2}\t完成条件:{3}";
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<Integer, ConcreteTask> taskEntry : map.entrySet()) {
            sb.append(MessageFormat.format(outputContent, taskEntry.getKey(), taskEntry.getValue().getTaskDescription(),
                    taskEntry.getValue().getBonus(), taskEntry.getValue().getCondition()) );
        }
        return sb.toString();
    }
    private Map<Integer, ConcreteTask> handleReceivableData(ConcreteRole role) {
        //查询数据库
        RoleTask roleTask = queryTask(role.getId());
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
     * 获取角色
     * @param channel channel
     * @return role
     */
    private ConcreteRole getRole(Channel channel) {
        Integer userId = LocalUserMap.getChannelUserMap().get(channel);
        ConcreteRole role = LocalUserMap.getUserRoleMap().get(userId);
        return role;
    }

    /**
     * 查询已接受任务
     * @param channel channel
     * @param requestTaskInfo 任务请求信息
     * @return 协议信息
     */
    public MsgTaskInfoProto.ResponseTaskInfo queryReceivedTask(Channel channel, MsgTaskInfoProto.RequestTaskInfo requestTaskInfo) {
        ConcreteRole role = getRole(channel);
        //查询数据库
        Map<Integer, ConcreteTask> receivedTaskMap = handleReceivedData(role);
        //打印消息
        String content = printMap(receivedTaskMap,role);
        //返回消息
        return MsgTaskInfoProto.ResponseTaskInfo.newBuilder()
                .setType(MsgTaskInfoProto.RequestType.QUERYRECEIVEDTASK)
                .setContent(content)
                .build();
    }

    /**
     * 处理数据
     * @param role 角色
     * @return map
     */
    private Map<Integer, ConcreteTask> handleReceivedData(ConcreteRole role) {
        //查询数据库
        RoleTask roleTask = queryTask(role.getId());
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
     *  接受任务
     * @param channel channel
     * @param requestTaskInfo 任务请求信息
     * @return 协议信息
     */
    public MsgTaskInfoProto.ResponseTaskInfo receiveTask(Channel channel, MsgTaskInfoProto.RequestTaskInfo requestTaskInfo) {
        //获取角色
        ConcreteRole role = getRole(channel);
        //taskId
        String taskId = requestTaskInfo.getTaskId();
        //查询
        RoleTask roleTask = queryTask(role.getId());
        String receivedTask = roleTask.getReceivedTask();
        StringBuffer sb = new StringBuffer(receivedTask);
        if(receivedTask==null){
            sb.append(taskId);
        }else{
            sb.append(",").append(taskId);
        }
        roleTask.setReceivedTask(sb.toString());
        //更新数据库信息
        updateTask(roleTask);
        //注册事件

        //todo 这里需要做一个扩展：任务的自动匹配

        String content = "成功接受任务";
        return MsgTaskInfoProto.ResponseTaskInfo.newBuilder()
                .setContent(content)
                .setType(MsgTaskInfoProto.RequestType.RECEIVETASK)
                .build();
    }
    /**
     * 放弃任务
     * @param channel channel
     * @param requestTaskInfo 任务请求信息
     * @return 协议信息
     */
    public MsgTaskInfoProto.ResponseTaskInfo discardTask(Channel channel, MsgTaskInfoProto.RequestTaskInfo requestTaskInfo) {
        //获取角色
        ConcreteRole role = getRole(channel);
        //taskId
        String taskId = requestTaskInfo.getTaskId();
        //更新数据
        updateData(role,taskId);
        String content = "成功放弃任务";
        return MsgTaskInfoProto.ResponseTaskInfo.newBuilder()
                .setType(MsgTaskInfoProto.RequestType.DISCARDTASK)
                .setContent(content)
                .build();
    }

    /**
     * 更新数据
     * @param role 角色
     * @param taskId 任务id
     */
    private void updateData(ConcreteRole role,String taskId) {
        //获取任务数据
        RoleTask roleTask = queryTask(role.getId());
        String receivedTask = roleTask.getReceivedTask();

        if(receivedTask==null){
            role.getChannel().writeAndFlush("没已接受的任务");
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
        updateTask(roleTask);
    }
}
