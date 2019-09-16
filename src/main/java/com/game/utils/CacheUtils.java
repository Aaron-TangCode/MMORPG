package com.game.utils;

import com.game.backpack.bean.GoodsResource;
import com.game.backpack.bean.GoodsType;
import com.game.buff.bean.ConcreteBuff;
import com.game.buff.handler.BuffType;
import com.game.map.bean.ConcreteMap;
import com.game.map.bean.MapMapping;
import com.game.npc.bean.ConcreteMonster;
import com.game.npc.bean.ConcreteNPC;
import com.game.npc.bean.MapNPCMapping;
import com.game.npc.bean.MonsterMapMapping;
import com.game.role.bean.ConcreteRole;
import com.game.role.repository.RoleRepository;
import com.game.skill.bean.ConcreteSkill;

import java.util.*;

/**
 * @ClassName CacheUtils
 * @Description 记录上线角色信息
 * @Author DELL
 * @Date 2019/6/310:58
 * @Version 1.0
 */
public class CacheUtils {

    private CacheUtils() {}
    /**
     * 用户名-角色缓存
     */
    private static volatile Map<String, ConcreteRole> mapUsernameRole = null;
    /**
     * 角色名-角色缓存
     */
    private static volatile Map<String,ConcreteRole> name2RoleMap = null;
    /**
     * 地图映射缓存
     */
    private static volatile List<MapMapping> mapList = null;

    /**
     * id_地图缓存
     */
    private static volatile Map<Integer, ConcreteMap> idConcreteMapMap = null;
    /**
     * name_地图缓存
     */
    private static volatile Map<String, ConcreteMap> nameConcreteMapMap = null;
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
     * key:name
     * value:goods
     */
    private static volatile Map<String, GoodsResource> goodsMap = null;
    /**
     * key:id
     * value:goods
     */
    private static volatile Map<Integer, GoodsResource> goodsMapById = null;
    /**
     * key:技能id
     * value:ConcreteSkill
     */
    private static volatile Map<String, ConcreteSkill> skillMap = null;
    /**
     * key:技能name+技能level
     * value:ConcreteSkill
     */
    private static volatile Map<String, ConcreteSkill> skillMap2 = null;
    /**
     * key:id
     * values:type
     */
    private static volatile Map<Integer, GoodsType> typeMap = null;
    /**
     * key:id
     * value:ConcreteBuff
     */
    private static volatile Map<BuffType, ConcreteBuff> buffMap = null;



    private static volatile List<GoodsResource> goodsList = null;

    private static volatile Map<Integer,ConcreteMap> mapMap = null;

    public static  Map<Integer,ConcreteMap> getMapMap(){
        if(mapMap ==null){
            synchronized (CacheUtils.class){
                if(mapMap ==null){
                    mapMap = new HashMap<>();
                }
            }
        }
        return mapMap;
    }
    public static List<GoodsResource> getGoodsList(){
        if(goodsList==null){
            synchronized (CacheUtils.class){
                if(goodsList==null){
                    goodsList = new ArrayList<>();
                }
            }
        }
        return goodsList;
    }

    public static Map<String, ConcreteMap> getNameConcreteMapMap(){
        if(nameConcreteMapMap ==null){
            synchronized (CacheUtils.class){
                if(nameConcreteMapMap ==null){
                    nameConcreteMapMap = new HashMap<>();
                }
            }
        }
        return nameConcreteMapMap;
    }
    /**
     * buff的map
     * @return
     */
    public static Map<BuffType, ConcreteBuff>  getBuffMap(){
        if(buffMap==null){
            synchronized (CacheUtils.class){
                if(buffMap==null){
                    buffMap = new HashMap<>();
                }
            }
        }
        return buffMap;
    }
    /**
     * 物品类型map
     * @return
     */
    public static Map<String, GoodsResource> getGoodsMap(){
        if(goodsMap==null){
            synchronized (CacheUtils.class){
                if(goodsMap==null){
                    goodsMap = new HashMap<>();
                }
            }
        }
        return goodsMap;
    }

    /**
     * 物品类型map
     * @return
     */
    public static Map<Integer, GoodsResource> getGoodsMapById(){
        if(goodsMapById==null){
            synchronized (CacheUtils.class){
                if(goodsMapById==null){
                    goodsMapById = new HashMap<>();
                }
            }
        }
        return goodsMapById;
    }
    /**
     * 物品类型map
     * @return
     */
    public static Map<Integer, GoodsType> getTypeMap(){
        if(typeMap==null){
            synchronized (CacheUtils.class){
                if(typeMap==null){
                    typeMap = new HashMap<>();
                }
            }
        }
        return typeMap;
    }
    /**
     * 技能map容器
     * @return
     */
    public static Map<String,ConcreteSkill> getSkillMapKeyName(){
        if(skillMap2==null){
            synchronized (CacheUtils.class){
                if(skillMap2==null){
                    skillMap2 = new HashMap<>();
                }
            }
        }
        return skillMap2;
    }
    /**
     * 技能map容器
     * @return
     */
    public static Map<String,ConcreteSkill> getSkillMapKeyId(){
        if(skillMap==null){
           synchronized (CacheUtils.class){
               if(skillMap==null){
                   skillMap = new HashMap<>();
               }
           }
        }
        return skillMap;
    }

    /**
     * map和monster映射容器
     * @return
     */
    public static List<MonsterMapMapping> getMonsterMapMappingList(){
        if(monsterMapMappingList==null){
            synchronized (CacheUtils.class){
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
            synchronized (CacheUtils.class){
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
    public static Map<Integer,List<Integer>> mapIdNpcIdMap(){
        Iterator<MapNPCMapping> iterator = getMapNpcMappingList().iterator();
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
    public static List<MapNPCMapping> getMapNpcMappingList(){
        if(mapNPCMappingList==null){
            synchronized (CacheUtils.class){
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
    public static Map<String, ConcreteRole> getMapUsernameRole() {
        if (mapUsernameRole == null) {
            synchronized (CacheUtils.class) {
                if (mapUsernameRole == null) {
                    mapUsernameRole = new HashMap<>();
                    return mapUsernameRole;
                }
            }
        }
        return mapUsernameRole;
    }

    /**
     * key:rolename角色名
     * value:Role实体类
     * @return
     */
    public static Map<String, ConcreteRole> getRoleByName() {
        if (name2RoleMap == null) {
            synchronized (CacheUtils.class) {
                if (name2RoleMap == null) {
                    name2RoleMap = new HashMap<>();
                    return name2RoleMap;
                }
            }
        }
        return name2RoleMap;
    }
    /**
     * 获取角色
     * @param name 角色名
     * @return 返回角色
     */
    public static ConcreteRole getRole(String name){
        ConcreteRole role = CacheUtils.getRoleByName().get(name);
        //如果为Null,就去数据找role
        if(role==null){
            RoleRepository roleRepository = new RoleRepository();
            role = roleRepository.getRoleByRoleName(name);
            //存储在本地
            addRole(name,role);
        }
        return role;
    }

    /**
     * 添加角色
     * @param name 角色名
     * @param role 角色
     */
    public static void addRole(String name,ConcreteRole role){
        CacheUtils.getRoleByName().put(name,role);
    }
    /**
     * 地图映射列表
     *
     * @return 地图Map_Mapping实体
     */
    public static List<MapMapping> getMapList() {
        if (mapList == null) {
            synchronized (CacheUtils.class) {
                if (mapList == null) {
                    mapList = new ArrayList<>();
                }
            }
        }
        return mapList;
    }

    /**
     * NPC容器
     * @return
     */
    public static Map<Integer, ConcreteNPC> getNpcMap(){
        if(npcMap==null){
            synchronized (CacheUtils.class){
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
     * @param srcId
     * @param destId
     * @return
     */
    public static boolean isReach(int srcId, int destId) {
        Iterator<MapMapping> iterator = getMapList().iterator();
        while (iterator.hasNext()) {
            MapMapping mapMapping = iterator.next();
            if (mapMapping.getSrcMap() == srcId && mapMapping.getDestMap() == destId) {
                return true;
            }
        }
        return false;
    }

    /**
     * 打印map
     * @param map map
     */
    public static void printMap(Map map){
        Set set = map.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()){
            Map.Entry next = (Map.Entry) iterator.next();
            System.out.println(next.getKey()+":"+next.getValue());
        }
    }


}
