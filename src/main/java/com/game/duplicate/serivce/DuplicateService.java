package com.game.duplicate.serivce;

import com.game.event.beanevent.AttackedEvent;
import com.game.event.manager.EventMap;
import com.game.map.bean.ConcreteMap;
import com.game.map.service.MapService;
import com.game.map.task.BossAutoAttackTask;
import com.game.map.threadpool.MapThreadPool;
import com.game.map.threadpool.TaskQueue;
import com.game.npc.bean.ConcreteMonster;
import com.game.protobuf.protoc.MsgBossInfoProto;
import com.game.role.bean.ConcreteRole;
import com.game.role.service.RoleService;
import com.game.skill.service.SkillService;
import com.game.user.manager.LocalUserMap;
import com.game.utils.MapUtils;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName DuplicateService
 * @Description 副本服务
 * @Author DELL
 * @Date 2019/8/12 17:35
 * @Version 1.0
 */
@Service
public class DuplicateService {
    /**
     * 角色服务
     */
    @Autowired
    private RoleService roleService;
    /**
     * 地图服务
     */
    @Autowired
    private MapService mapService;
    /**
     * 被攻击事件
     */
    @Autowired
    private AttackedEvent attackedEvent;
    /**
     * 事件驱动
     */
    @Autowired
    private EventMap eventMap;

    @Autowired
    private SkillService skillService;

    /**
     * 攻击boss
     * @param channel channel
     * @param requestBossInfo Boss请求信息
     * @return
     */
    public MsgBossInfoProto.ResponseBossInfo attackBoss(Channel channel, MsgBossInfoProto.RequestBossInfo requestBossInfo) {
        //role
        ConcreteRole tmpRole = getRole(channel);
        //bossName
        String bossName = requestBossInfo.getBossName();
        //skillName
        String skillName = requestBossInfo.getSkillName();
        //mapName
        String mapName = requestBossInfo.getMapName();
        //队伍列表
        List<ConcreteRole> roleList = new ArrayList<>();
        //获取角色信息
        ConcreteRole role = roleService.getRoleByRoleName(tmpRole.getName());
        //把角色添加到队伍列表
        roleList.add(role);
        //初始化角色
        String content = initRole(roleList,bossName,mapName,skillName);
        return MsgBossInfoProto.ResponseBossInfo.newBuilder()
                .setContent(content)
                .setType(MsgBossInfoProto.RequestType.ATTACKBOSS)
                .build();
    }
    /**
     * 副本模块的统一入口，初始化副本
     * @param roleList 角色列表
     * @param bossName boss名字
     * @param mapName 地图名字
     * @param skillName 技能
     * @return 消息
     */
    public String initRole(List<ConcreteRole> roleList,String bossName, String mapName, String skillName) {
        //获取地图id
        int mapId = roleList.get(0).getConcreteMap().getId();
        //获取临时地图
        ConcreteMap tmpMap = mapService.getMap(mapId);
        //新副本
        ConcreteMap map = new ConcreteMap(tmpMap);
        //把角色添加到新副本
        for(ConcreteRole role:roleList){
            String roleName = role.getName();
            //切换地图
            mapService.moveTo(roleName, mapName);
            //把角色添加到地图
            map.getRoleList().add(role);
        }
        //获取Boss列表
        List<Integer> list = skillService.findMonster(mapId);
        //找出地图的boss
        Map<Integer, ConcreteMonster> bossMap = findConcreteMonster(map,list, bossName);
        //boss添加到地图
        map.setMonsterMap(bossMap);

        //boss的自动攻击
        autoAttack(map);

        return  "刷副本";
    }

    /**
     * 自动攻击
     * @param map 地图
     */
    public void autoAttack(ConcreteMap map){
        //todo:怪物根据角色的职业的吸引值优先进行攻击
        //遍历角色的仇恨值，选出最大的一个来攻击
        ConcreteRole tmpRole = chooseRole(map);
        ConcreteRole mostRole = MapUtils.getMapRolename_Role().get(tmpRole.getName());


        //触发仇恨值最大的角色被攻击事件
        attackedEvent.setRole(mostRole);
        eventMap.submit(attackedEvent);
        //选择线程池中的某一个线程处理
        int threadIndex = MapThreadPool.getThreadIndex(map);
        List<ConcreteRole> roleList = map.getRoleList();
        Map<Integer, ConcreteMonster> bossMap = map.getMonsterMap();
        //创建新任务
        BossAutoAttackTask task = new BossAutoAttackTask(mostRole,bossMap);
        //添加任务到队列
        TaskQueue.getQueue().add(task);
        //线程执行任务
        MapThreadPool.ACCOUNT_SERVICE[threadIndex].scheduleAtFixedRate( () ->{
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
     * @param map 地图
     * @return 角色
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
     * 找出地图的Boss
     * @param map 地图
     * @param monsterList 怪兽列表
     * @param monsterName 怪兽名字
     * @return 集合
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
     * 获取角色
     * @param channel channel
     * @return 角色
     */
    public ConcreteRole getRole(Channel channel){
        Integer userId = LocalUserMap.getChannelUserMap().get(channel);
        ConcreteRole role = LocalUserMap.getUserRoleMap().get(userId);
        return role;
    }
}
