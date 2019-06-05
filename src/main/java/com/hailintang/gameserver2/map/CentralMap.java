package com.hailintang.gameserver2.map;


import com.hailintang.gameserver2.role.ParentRole;
import com.hailintang.gameserver2.role.Role_02;

import java.util.List;

/**
 * @ClassName CentralMap
 * @Description 存储地图信息和判断地图是否可达
 * @Author DELL
 * @Date 2019/5/2411:15
 * @Version 1.0
 */
public class CentralMap {
   static int[][] reachMap = new int[][]{
           {1,2},
           {2,1,3,4},
           {3,2,5},
           {4,2},
           {5,3}
   };

    /**
     * 判断地点是否可达
     * @param src
     * @param dest
     * @return
     */
   public static boolean isReach(Map src,Map dest){
       //拿到id
       int srcId = src.getId();
       int destId = dest.getId();
       int len = reachMap[srcId-1].length;

       //处理逻辑，进行判断(遍历某一行的列)
       for(int i=1;i<len;i++){
           if(reachMap[srcId-1][i]==destId){
               return true;
           }
       }

       return false;
   }
    public static void handleRole(Map src, Map dest, ParentRole role){
        //在src的容器中去掉role
        src.getList().remove(role);
        //添加role到dest中
        dest.getList().add(role);

        //添加list到map中
        CentralMap.addListToMapInfo(dest,dest.getList());
    }

    /**
     * 添加list到HashMap中
     * @param map 地图
     * @param list 角色信息list
     */
    public static void addListToMapInfo(Map map, List<ParentRole> list){
       MapInfo.getMap().put(map.getId(),list);
    }

    public static Map moveTo(Map src, Map dest, ParentRole role) {
        if(isReach(src,dest)){
            //添加role到场景中
            CentralMap.handleRole(src,dest,role);
            return dest;

        }else{
            return src;
        }
    }
}
