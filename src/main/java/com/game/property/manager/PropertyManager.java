package com.game.property.manager;

import com.game.property.StateValue;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName PropertyManager
 * @Description map(String,StateValue)
 * @Author DELL
 * @Date 2019/6/20 21:36
 * @Version 1.0
 */
public class PropertyManager {
    private static volatile Map<String, StateValue> map = null;

    private PropertyManager(){}

    public static Map<String,StateValue> getMap(){
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
