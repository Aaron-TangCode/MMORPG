package com.game.utils;

import com.game.excel.bean.MapMapping;
import com.game.npc.bean.ConcreteMonster;
import com.game.npc.bean.ConcreteNPC;
import com.game.npc.bean.MapNPCMapping;
import com.game.npc.bean.MonsterMapMapping;
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
    private MapUtils() {}
    /**
     * 用户名-角色缓存
     */
    private static volatile Map<String, ConcreteRole> mapUsername_Role = null;
    /**
     * 角色名-角色缓存
     */
    private static volatile Map<String,ConcreteRole> mapRolename_Role = null;
    /**
     * 地图缓存
     */
    private static volatile List<MapMapping> listRole = null;

    /**
     * NPC容器
     */
    private static volatile Map<Integer,ConcreteNPC> npcMap = null;
    /**
     *
     * map和npc的映射容器
     */
    private static volatile List<MapNPCMapping> mapNPCMappingList = null;
    /**
     * key:monster的id
     * value:ConcreteMonster
     * 怪兽容器
     */
    private static volatile Map<Integer, ConcreteMonster> monsterMap = null;
    /**
     *map和monster映射容器
     */
    private static volatile List<MonsterMapMapping> monsterMapMappingList = null;

    /**
     * map和monster映射容器
     * @return
     */
    public static List<MonsterMapMapping> getMonsterMapMappingList(){
        if(monsterMapMappingList==null){
            synchronized (MapUtils.class){
                if(monsterMapMappingList==null){
                    monsterMapMappingList = new ArrayList<>();
                }
            }
        }
        return monsterMapMappingList;
    }

    /**
     * 怪兽容器
     * @return
     */
    public static Map<Integer, ConcreteMonster> getMonsterMap(){
        if(monsterMap==null){
            synchronized (MapUtils.class){
                if (monsterMap==null){
                    monsterMap = new HashMap<>();
                }
            }
        }
        return monsterMap;
    }


    /**
     * map<mapId,list<npcId>>
     */
    public static Map<Integer,List<Integer>> mapIdnpcIdMap(){
        Iterator<MapNPCMapping> iterator = getMapNPCMappingList().iterator();
        Map<Integer,List<Integer>> mapID_npcId_Map = new HashMap<>();
        List<Integer> list = null;
        while(iterator.hasNext()){
            MapNPCMapping mapNPCMapping = iterator.next();
            if (mapID_npcId_Map.containsKey(mapNPCMapping.getMapId())) {
                list.add(mapNPCMapping.getNpcId());
                mapID_npcId_Map.put(mapNPCMapping.getMapId(),list);
            }else{
                list = new ArrayList<>();
                list.add(mapNPCMapping.getNpcId());
                mapID_npcId_Map.put(mapNPCMapping.getMapId(),list);
            }
        }
        return mapID_npcId_Map;
    }
    /**
     * 地图map和npc的映射容器
     * @return
     */
    public static List<MapNPCMapping> getMapNPCMappingList(){
        if(mapNPCMappingList==null){
            synchronized (MapUtils.class){
                if(mapNPCMappingList==null){
                    mapNPCMappingList = new ArrayList<>();
                }
            }
        }
        return mapNPCMappingList;
    }
    /**
     * 地图角色map
     * @return 用户名(非角色名)，role实体
     */
    public static Map<String, ConcreteRole> getMapUsername_Role() {
        if (mapUsername_Role == null) {
            synchronized (MapUtils.class) {
                if (mapUsername_Role == null) {
                    mapUsername_Role = new HashMap<>();
                    return mapUsername_Role;
                }
            }
        }
        return mapUsername_Role;
    }

    /**
     * key:rolename角色名
     * value:Role实体类
     * @return
     */
    public static Map<String, ConcreteRole> getMapRolename_Role() {
        if (mapRolename_Role == null) {
            synchronized (MapUtils.class) {
                if (mapRolename_Role == null) {
                    mapRolename_Role = new HashMap<>();
                    return mapRolename_Role;
                }
            }
        }
        return mapRolename_Role;
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
                }
            }
        }
        return listRole;
    }

    /**
     * NPC容器
     * @return
     */
    public static Map<Integer, ConcreteNPC> getNpcMap(){
        if(npcMap==null){
            synchronized (MapUtils.class){
                if(npcMap==null){
                    npcMap = new HashMap<>();
                }
            }
        }
        return npcMap;
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
