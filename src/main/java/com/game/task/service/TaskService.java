package com.game.task.service;

import com.game.task.bean.RoleTask;
import com.game.task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
