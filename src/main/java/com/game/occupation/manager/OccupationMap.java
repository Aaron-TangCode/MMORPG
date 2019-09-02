package com.game.occupation.manager;

import com.game.occupation.bean.Occupation;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName OccupationMap
 * @Description 职业map
 * @Author DELL
 * @Date 2019/7/3 15:27
 * @Version 1.0
 */
public class OccupationMap {
    /**
     * 职业容器map
     */
    private static volatile Map<Integer, Occupation> occupationMap = null;
    private OccupationMap(){}

    /**
     * 获取职业容器map
     * @return map
     */
    public static Map<Integer, Occupation> getOccupationMap(){
        if(occupationMap==null){
            synchronized (OccupationMap.class){
                if (occupationMap==null){
                    occupationMap = new HashMap<>();
                }
            }
        }
        return occupationMap;
    }
}
