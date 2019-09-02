package com.game.task.manager;

import com.game.task.bean.ConcreteTask;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName TaskMap
 * @Description 任务map
 * @Author DELL
 * @Date 2019/7/22 16:06
 * @Version 1.0
 */
public class TaskMap {
    /**
     * 任务容器map
     */
    private static volatile Map<Integer, ConcreteTask> taskMap = new HashMap<>();

    private TaskMap(){}

    public static Map<Integer,ConcreteTask> getTaskMap(){
        return taskMap;
    }
}
