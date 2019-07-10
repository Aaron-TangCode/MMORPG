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
import io.netty.channel.ChannelHandlerContext;
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
    @RequestAnnotation("/attackboss")
    public String attackBoss(String roleName,String bossName,String mapName,String skillName){


        List<ConcreteRole> roleList = new ArrayList<>();
        ConcreteRole role = roleService.getRoleByRoleName(roleName);
        roleList.add(role);
        initRole(roleList,bossName,mapName,skillName);


    }

    public String initRole(List<ConcreteRole> roleList,String bossName, String mapName, String skillName) {
        //获取地图id
        int mapId = roleList.get(1).getConcreteMap().getId();
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

        return  null;
    }

    public Map<Integer,ConcreteMonster> findConcreteMonster(ConcreteMap map,List<Integer> monsterList, String monsterName) {
        //获取地图中的怪兽集合
        Map<Integer, ConcreteMonster> monsterMap = map.getMonsterMap();

        ConcreteMonster monster = null;
        for (int i = 0; i < monsterList.size(); i++) {
            ConcreteMonster concreteMonster = MapUtils.getMonsterMap().get(monsterList.get(i));
            if (Objects.equals(concreteMonster.getName(),monsterName)) {
               monsterMap.put(monster.getId(),monster);
            }
        }
        return monsterMap;
    }
    /**
     * 怪物的自动攻击
     */
    public void autoAttack(ConcreteMap map){
        //遍历角色的仇恨值，选出最大的一个来攻击
        ConcreteRole mostRole = chooseRole(map);


        //todo:怪物根据角色的职业的吸引值优先进行攻击
        ConcreteRole role = MapUtils.getMapRolename_Role().get(roleName);
        ChannelHandlerContext ctx = role.getCtx();
        int id = Math.abs(ctx.channel().id().hashCode());
        int modIndex = id% MapThreadPool.DEFAULT_THREAD_POOL_SIZE;
        Task task = new Task(role,bossMap);
        TaskQueue.getQueue().add(task);

        MapThreadPool.ACCOUNT_SERVICE[modIndex].scheduleAtFixedRate( () ->{
                    Iterator<Runnable> iterator = TaskQueue.getQueue().iterator();
                    while (iterator.hasNext()) {
                        Runnable runnable = iterator.next();
                        if (Objects.nonNull(runnable)) {
                            runnable.run();
                        }
                    }

                },
                5L,boss.getAttackTime(), TimeUnit.SECONDS);


    }

    /**
     * 选出玩家的仇恨值最高的那个
     * @param map
     * @return
     */
    private ConcreteRole chooseRole(ConcreteMap map) {
        List<ConcreteRole> roleList = map.getRoleList();
        ConcreteRole resRole = roleList.get(0);
        for (int i = 1; i < roleList.size(); i++) {
             Math.max(roleList.get(i).getOccupation().getAttract(),roleList.get(i-1).getOccupation().getAttract());
        }
        return null;
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
