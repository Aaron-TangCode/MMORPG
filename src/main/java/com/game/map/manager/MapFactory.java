package com.game.map.manager;

import com.game.map.bean.ConcreteMap;

/**
 * @ClassName MapFactory
 * @Description 地图工厂
 * @Author DELL
 * @Date 2019/9/6 18:19
 * @Version 1.0
 */
public class MapFactory {
    public static ConcreteMap createMap(int id, String name){
        return new ConcreteMap(id,name);
    }
}
