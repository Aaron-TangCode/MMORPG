package com.game.duplicate.manager;

import com.game.map.bean.ConcreteMap;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName RoleAndMap
 * @Description Role-map
 * @Author DELL
 * @Date 2019/8/26 11:22
 * @Version 1.0
 */
public class RoleAndMap {
    /**
     * 当前血量
     */
    public static volatile Integer varHp = 0;
    /**
     * key:roleName
     * value:map
     */
    private static volatile Map<String, ConcreteMap> map = new HashMap<>();

    private RoleAndMap(){}

    public static Map<String, ConcreteMap> getRoleAndMap(){
        return map;
    }
}
