package com.game.buff.manager;

import com.game.buff.controller.Task;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName BuffMapManager
 * @Description TODO
 * @Author DELL
 * @Date 2019/6/20 18:22
 * @Version 1.0
 */
public class BuffMapManager {
    private static volatile Map<Integer, Task> taskMap = null;

    private BuffMapManager(){}

    public static Map<Integer, Task> getTaskMap(){
        if(taskMap==null){
            synchronized (BuffMapManager.class){
                if(taskMap==null){
                    taskMap = new HashMap<>();
                }
            }
        }
        return taskMap;
    }
}
