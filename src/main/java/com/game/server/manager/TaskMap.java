package com.game.server.manager;

import io.netty.util.concurrent.Future;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName TaskMap
 * @Description 任务容器map
 * @Author DELL
 * @Date 2019/8/14 21:25
 * @Version 1.0
 */
public class TaskMap {
    private TaskMap(){}

    public static Map<String, Future> getFutureMap(){
        return new ConcurrentHashMap<>();
    }
}
