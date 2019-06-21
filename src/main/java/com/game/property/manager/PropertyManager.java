package com.game.property.manager;

import com.game.property.bean.Property;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName PropertyManager
 * @Description Map<Integer, Property>
 * @Author DELL
 * @Date 2019/6/20 21:36
 * @Version 1.0
 */
public class PropertyManager {
    /**
     * key:Level
     * value:property
     * 存储property的等级信息
     */
    private static volatile Map<Integer, Property> map = null;

    private PropertyManager(){}

    public static Map<Integer,Property> getMap(){
        if(map==null){
           synchronized (PropertyManager.class){
               if(map==null){
                   map = new HashMap<>();
               }
           }
        }
        return map;
    }
}
