package com.game.task.manager;

import com.game.role.bean.ConcreteRole;
import com.game.task.bean.ConcreteTask;
import com.game.task.bean.RoleTask;
import com.game.task.repository.TaskRepository;
import com.game.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName InjectTaskData
 * @Description 注入任务数据
 * @Author DELL
 * @Date 2019/7/22 17:25
 * @Version 1.0
 */
@Component
public class InjectTaskData {
    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    /**
     * 注入数据，缓存本地
     */
    public void injectData(ConcreteRole role){
        //获取本地的任务map（所有任务）
        Map<Integer, ConcreteTask> taskMap = TaskMap.getTaskMap();
        //分配任务
        allocateTask(taskMap,role);
    }

    private void allocateTask(Map<Integer, ConcreteTask> taskMap,ConcreteRole role) {
        RoleTask roleTask = taskService.queryTask(role.getId());
        //存进不同的taskmap中(finishedTask的数据格式：1,2,3)
        String finishedTask = roleTask.getFinishedTask();
        String receivedTask = roleTask.getReceivedTask();
        //解析数据
        String[] fTask = finishedTask.split(",");
        String[] rTask = receivedTask.split(",");
        //赋值
        Map<Integer,ConcreteTask> raMap = new HashMap<>();
        Map<Integer,ConcreteTask> rMap = new HashMap<>();
        Map<Integer,ConcreteTask> fMap = new HashMap<>();

        role.setReceivedTaskMap(rMap);
        role.setFinishedTaskMap(fMap);
        //创建临时taskmap
        Map<Integer,ConcreteTask> tmpTaskMap = new HashMap<>(taskMap);
        //遍历    分配“已完成”的任务
        for (int i = 0; i < fTask.length; i++) {
            ConcreteTask tmpTask = tmpTaskMap.remove(Integer.parseInt(fTask[i]));
            role.getFinishedTaskMap().put(tmpTask.getId(),tmpTask);
        }
        //遍历    分配“已接受”的任务
        for (int i = 0; i < rTask.length; i++) {
            ConcreteTask tmpTask = tmpTaskMap.remove(Integer.parseInt(rTask[i]));
            role.getReceivedTaskMap().put(tmpTask.getId(),tmpTask);
        }
        raMap = tmpTaskMap;
        role.setReceivableTaskMap(raMap);
    }
}
