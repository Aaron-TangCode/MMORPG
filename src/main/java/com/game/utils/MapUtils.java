package com.game.utils;

import com.game.excel.bean.MapMapping;
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
    /**
     * 角色缓存
     */
    private static volatile Map<String, ConcreteRole> mapRole = null;
    /**
     * 地图缓存
     */
    private static volatile List<MapMapping> listRole = null;
    /**
     * 任务缓存
     */
    private static volatile Map<String, ArrayList<RequestTask>> taskMap = null;


    private MapUtils() {
    }


    /**
     * 地图角色map
     *
     * @return 用户名(非角色名)，role实体
     */
    public static Map<String, ConcreteRole> getMapRole() {
        if (mapRole == null) {
            synchronized (MapUtils.class) {
                if (mapRole == null) {
                    mapRole = new HashMap<>();
                    return mapRole;
                }
            }
        }
        return mapRole;
    }

    /**
     * 获取任务缓存对象
     * @return
     */
    public static Map<String, ArrayList<RequestTask>> getTaskMap() {
        if (taskMap == null) {
            synchronized (MapUtils.class) {
                if (taskMap == null) {
                    taskMap = new HashMap<>();
                }
            }
        }
        return taskMap;
    }
    /**
     * 地图映射列表
     *
     * @return 地图Map_Mapping实体
     */
    public static List<MapMapping> getListRole() {
        if (listRole == null) {
            synchronized (MapUtils.class) {
                if (listRole == null) {
                    listRole = new ArrayList<>();
                    return listRole;
                }
            }
        }
        return listRole;
    }

    /**
     * 切换地图是否可达
     *
     * @param src_id
     * @param dest_id
     * @return
     */
    public static boolean isReach(int src_id, int dest_id) {
        Iterator<MapMapping> iterator = getListRole().iterator();
        while (iterator.hasNext()) {
            MapMapping mapMapping = iterator.next();
            if (mapMapping.getSrcMap() == src_id && mapMapping.getDestMap() == dest_id) {
                return true;
            }
        }
        return false;
    }
}
