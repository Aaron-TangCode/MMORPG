package com.game.duplicate;

import com.game.dispatcher.RequestAnnotation;
import com.game.map.bean.ConcreteMap;
import com.game.map.controller.MapController;
import com.game.map.task.Task;
import com.game.map.threadpool.MapThreadPool;
import com.game.map.threadpool.TaskQueue;
import com.game.npc.bean.ConcreteMonster;
import com.game.npc.bean.MonsterMapMapping;
import com.game.role.bean.ConcreteRole;
import com.game.role.service.RoleService;
import com.game.skill.controller.SkillController;
import com.game.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName DuplicateController
 * @Description 副本控制器
 * @Author DELL
 * @Date 2019/7/8 15:01
 * @Version 1.0
 */
@RequestAnnotation("/duplicate")
@Controller
public class DuplicateController {

    @Autowired
    private MapController mapController;

    @Autowired
    private SkillController skillController;

    @Autowired
    private RoleService roleService;

    /**
     * 一个角色战斗
     * @param roleName
     * @param bossName
     * @param mapName
     * @param skillName
     * @return
     */
    @RequestAnnotation("/attackboss1")
    public String attackBoss(String roleName,String bossName,String mapName,String skillName){
        //队伍列表
        List<ConcreteRole> roleList = new ArrayList<>();
        //获取角色信息
        ConcreteRole role = roleService.getRoleByRoleName(roleName);
        //把角色添加到队伍列表
        roleList.add(role);
        //初始化角色
       return initRole(roleList,bossName,mapName,skillName);


    }

    /**
     * 2个角色战斗
     * @param roleName1
     * @param roleName2
     * @param bossName
     * @param mapName
     * @param skillName
     * @return
     */
    @RequestAnnotation("/attackboss2")
    public String attackBoss(String roleName1,String roleName2,String bossName,String mapName,String skillName){
        //队伍列表
        List<ConcreteRole> roleList = new ArrayList<>();
        //获取角色信息
        ConcreteRole role1 = roleService.getRoleByRoleName(roleName1);
        ConcreteRole role2 = roleService.getRoleByRoleName(roleName2);

        //把角色添加到队伍列表
        roleList.add(role1);
        roleList.add(role2);
        //初始化角色
        return initRole(roleList,bossName,mapName,skillName);

    }

    /**
     * 副本模块的统一入口，初始化副本
     * @param roleList
     * @param bossName
     * @param mapName
     * @param skillName
     * @return
     */
    public String initRole(List<ConcreteRole> roleList,String bossName, String mapName, String skillName) {
        //获取地图id
        int mapId = roleList.get(0).getConcreteMap().getId();
        //获取临时地图
        ConcreteMap tmpMap = mapController.getMap(mapId);
        //新副本
        ConcreteMap map = new ConcreteMap(tmpMap);
       //把角色添加到新副本
        for(ConcreteRole role:roleList){
            String roleName = role.getName();
            //切换地图
            mapController.moveTo(roleName, mapName);
            //把角色添加到地图
            map.getRoleList().add(role);
        }
        //获取Boss列表
        List<Integer> list = prepareForAttack(mapId);
        //找出地图的boss
        Map<Integer, ConcreteMonster> bossMap = findConcreteMonster(map,list, bossName);
        //boss添加到地图
        map.setMonsterMap(bossMap);

        //boss的自动攻击
        autoAttack(map);

        return  "刷副本";
    }

    /**
     * 找出地图的Boss
     * @param map
     * @param monsterList
     * @param monsterName
     * @return
     */
    public Map<Integer,ConcreteMonster> findConcreteMonster(ConcreteMap map,List<Integer> monsterList, String monsterName) {
        //获取地图中的怪兽集合
        Map<Integer, ConcreteMonster> monsterMap = map.getMonsterMap();


        for (int i = 0; i < monsterList.size(); i++) {
            ConcreteMonster concreteMonster = MapUtils.getMonsterMap().get(monsterList.get(i));
            if (Objects.equals(concreteMonster.getName(),monsterName)) {
               monsterMap.put(concreteMonster.getId(),concreteMonster);
            }
        }
        return monsterMap;
    }
    /**
     * 怪物的自动攻击
     */
    public void autoAttack(ConcreteMap map){
        //todo:怪物根据角色的职业的吸引值优先进行攻击
        //遍历角色的仇恨值，选出最大的一个来攻击
        ConcreteRole tmpRole = chooseRole(map);
        ConcreteRole mostRole = MapUtils.getMapRolename_Role().get(tmpRole.getName());

        //选择线程池中的某一个线程处理
        int id = Math.abs(map.hashCode());
        int modIndex = id% MapThreadPool.DEFAULT_THREAD_POOL_SIZE;
        List<ConcreteRole> roleList = map.getRoleList();
        Map<Integer, ConcreteMonster> bossMap = map.getMonsterMap();
        //创建新任务
        Task task = new Task(mostRole,bossMap);
        //添加任务到队列
        TaskQueue.getQueue().add(task);
        //线程执行任务
        MapThreadPool.ACCOUNT_SERVICE[modIndex].scheduleAtFixedRate( () ->{
                    Iterator<Runnable> iterator = TaskQueue.getQueue().iterator();
                    while (iterator.hasNext()) {
                        Runnable runnable = iterator.next();
                        if (Objects.nonNull(runnable)) {
                            runnable.run();
                        }
                    }

                },
                5L,5L, TimeUnit.SECONDS);


    }

    /**
     * 选出玩家的仇恨值最高的那个
     * @param map
     * @return
     */
    private ConcreteRole chooseRole(ConcreteMap map) {
        //获取角色列表
        List<ConcreteRole> roleList = map.getRoleList();
        //初始化角色
        ConcreteRole resRole = roleList.get(0);
        //选出仇恨值最高的角色
        for (int i = 1; i < roleList.size(); i++) {
             int bigger = Math.max(roleList.get(i).getOccupation().getAttract(),roleList.get(i-1).getOccupation().getAttract());
             resRole =  roleList.get(i);
        }
        return resRole;
    }

    /**
     * 准备攻击
     * @param mapId
     * @return
     */
    public List<Integer> prepareForAttack(int mapId) {
        //获取地图上的怪兽列表
        List<Integer> monsterList = new ArrayList<>();
        //地图和怪兽的映射列表
        List<MonsterMapMapping> monsterMapMappingList = MapUtils.getMonsterMapMappingList();
        //遍历找出具体的怪兽列表
        for (int i = 0; i < monsterMapMappingList.size(); i++) {
            if(monsterMapMappingList.get(i).getMapId()==mapId){
                monsterList.add(monsterMapMappingList.get(i).getMonsterId());
            }
        }
        return monsterList;
    }

}
