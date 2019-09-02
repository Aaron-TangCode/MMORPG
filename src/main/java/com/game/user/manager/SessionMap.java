package com.game.user.manager;

import com.game.role.bean.ConcreteRole;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName SessionMap
 * @Description 存储账号
 * @Author DELL
 * @Date 2019/8/2 17:52
 * @Version 1.0
 */
public class SessionMap {
    /**
     * session容器map
     */
    private static volatile Map<String, ConcreteRole> sessionMap = new HashMap<>();

    private SessionMap(){}

    /**
     * key:username
     * value:role
     * @return
     */
    public static Map<String,ConcreteRole> getSessionMap(){
        return sessionMap;
    }
}
