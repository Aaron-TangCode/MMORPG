package com.game.utils;

import com.game.map.Map_Mapping;
import com.game.role.bean.ConcreteRole;

import java.util.*;

/**
 * @ClassName MapUtils
 * @Description 记录上线角色信息
 * @Author DELL
 * @Date 2019/6/310:58
 * @Version 1.0
 */
public class MapUtils {
    private static volatile Map<String,ConcreteRole> mapRole = null;
    private static volatile List<Map_Mapping> listRole = null;
    private static volatile Map<String,String> reslutCollections = null;
    private MapUtils(){
    }

    /**
     * 地图角色map
     * @return 用户名(非角色名)，role实体
     */
    public static Map<String,ConcreteRole> getMapRole() {
        if(mapRole==null){
            synchronized (MapUtils.class){
                if (mapRole==null){
                    mapRole = new HashMap<>();
                    return mapRole;
                }
            }
        }
        return mapRole;
    }

    /**
     * 地图映射列表
     * @return 地图Map_Mapping实体
     */
    public static List<Map_Mapping> getListRole() {
        if(listRole==null){
            synchronized (MapUtils.class){
                if (listRole==null){
                    listRole = new ArrayList<>();
                    return listRole;
                }
            }
        }
        return listRole;
    }

    /**
     * 切换地图是否可达
     * @param src_id
     * @param dest_id
     * @return
     */
    public static  boolean isReach(int src_id,int dest_id){
        Iterator<Map_Mapping> iterator = getListRole().iterator();
        while(iterator.hasNext()){
            Map_Mapping mapMapping = iterator.next();
            if(mapMapping.getSrc_map()==src_id&&mapMapping.getDest_map()==dest_id){
                return true;
            }
        }
        return false;
    }

    /**
     * 第一个String：存放客户端的socket
     * 第二个String:存放返回客户端的内容
     * @return
     */
    public static Map<String,String> getResultCollectionsInstance(){
        if(reslutCollections==null){
            synchronized (MapUtils.class){
                if(reslutCollections==null){
                    reslutCollections = new  HashMap<String,String>();
                    return reslutCollections;
                }
            }
        }
        return reslutCollections;
    }
}
