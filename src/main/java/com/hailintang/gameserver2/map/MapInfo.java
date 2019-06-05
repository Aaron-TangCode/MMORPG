package com.hailintang.gameserver2.map;

import com.hailintang.gameserver2.role.ParentRole;

import java.util.*;
import java.util.Map;

/**
 * @ClassName MapInfo
 * @Description 当前场景的所有信息
 * @Author DELL
 * @Date 2019/5/2415:22
 * @Version 1.0
 */
public class MapInfo{
    private static Map<Integer,List<ParentRole>> map = new HashMap<>();

    /**
     * map包含当前场景的所有角色信息
     * @return
     */
    public static Map<Integer,List<ParentRole>> getMap(){
        return map;
    }

    /**
     * 打印某个场景的所有角色信息
     */
    public static void printCurMapAllOfRoleInfo(com.hailintang.gameserver2.map.Map curMap){
        Map<Integer,List<ParentRole>> map = getMap();
        //获得List
        List<ParentRole> list = map.get(curMap.getId());
        //获得role以及遍历role的信息

        if(list==null||list.size()==0){
            System.out.println("该场景没任何玩家信息");
        }else{
            for(ParentRole role:list){
                System.out.println(role+","+"在"+curMap.getName()+","+"状态："+role.getState().getStateName());
            }
        }



    }

}
