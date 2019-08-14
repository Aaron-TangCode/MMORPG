package com.game.server.manager;

import io.netty.util.concurrent.Future;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName TaskMap
 * @Description TODO
 * @Author DELL
 * @Date 2019/8/14 21:25
 * @Version 1.0
 */
public class TaskMap {
    private static volatile Map<String, Future> futureMap = new ConcurrentHashMap<>();

    private TaskMap(){}

    public static Map<String, Future> getFutureMap(){
        return futureMap;
    }
}
