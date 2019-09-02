package com.game.server.manager;

import com.game.buff.bean.ConcreteBuff;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName TaskMap
 * @Description buff容器map
 * @Author DELL
 * @Date 2019/8/14 21:25
 * @Version 1.0
 */
public class BuffMap {
    private BuffMap(){}

    public static Map<String, ConcreteBuff> getBuffMap(){
        return new ConcurrentHashMap<>();
    }
}
