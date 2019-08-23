package com.game.duplicate.serivce;

import com.game.buff.bean.ConcreteBuff;
import com.game.duplicate.manager.TeamMapManager;
import com.game.duplicate.task.RoleAttackBossTask;
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
import com.game.server.manager.BuffMap;
import com.game.server.manager.TaskMap;
import com.game.skill.service.SkillService;
import com.game.user.manager.LocalUserMap;
import com.game.utils.MapUtils;
import io.netty.channel.Channel;
import io.netty.util.concurrent.Future;
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
     * 团队打Boss
     * @param channel channel
     * @param requestBossInfo requestBossInfo
     * @return
     */
    public MsgBossInfoProto.ResponseBossInfo teamAttackBoss(Channel channel, MsgBossInfoProto.RequestBossInfo requestBossInfo) {
        //role
        ConcreteRole role = getRole(channel);
        //roleTeamMap --> getTeam
        Map<String, String> roleTeamMap = TeamMapManager.getRoleTeamMap();
        String teamName = roleTeamMap.get(role.getName());
        //getTeam
        Map<String, List<ConcreteRole>> teamMap = TeamMapManager.getTeamMap();
        //roleList
        List<ConcreteRole> roleList = teamMap.get(teamName);
        //bossName
        String bossName = requestBossInfo.getBossName();
        //mapName
        String mapName = requestBossInfo.getMapName();

        //初始化角色
        String content = initRole(roleList,bossName,mapName,null);
        return MsgBossInfoProto.ResponseBossInfo.newBuilder()
                .setContent(content)
                .setType(MsgBossInfoProto.RequestType.TEAMATTACKBOSS)
                .build();
    }
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
        //玩家自动攻击boss
//        roleAttackBoss(map);
        //boss的自动攻击
        autoAttack(map);
        return  "刷副本";
    }

    private void roleAttackBoss(ConcreteMap map) {
        //roleList
        List<ConcreteRole> roleList = map.getRoleList();
        //boss
        Map<Integer, ConcreteMonster> monsterMap = map.getMonsterMap();
        //task
        RoleAttackBossTask task = new RoleAttackBossTask(roleList,monsterMap,skillService,map);
        ConcreteRole captain = roleList.get(0);
        //选择线程池中的某一个线程处理
        int threadIndex = MapThreadPool.getThreadIndex(map);
        //初始化role和buff
        initRoleTask(captain,TaskQueue.getQueue(), TaskMap.getFutureMap(), BuffMap.getBuffMap());
        //添加任务到队列
        captain.getQueue().add(task);
        //线程执行任务
        MapThreadPool.ACCOUNT_SERVICE[threadIndex].scheduleAtFixedRate( () ->{
                    Iterator<Runnable> iterator = captain.getQueue().iterator();
                    while (iterator.hasNext()) {
                        Runnable runnable = iterator.next();
                        if (Objects.nonNull(runnable)) {
                            runnable.run();
                        }
                    }

                },
                4L,8L, TimeUnit.SECONDS);

    }

    /**
     * 自动攻击
     * @param map 地图
     */
    public void autoAttack(ConcreteMap map){
        //怪物根据角色的职业的吸引值优先进行攻击
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
        BossAutoAttackTask task = new BossAutoAttackTask(mostRole,bossMap,map);
        //初始化role和buff
        initRoleTask(mostRole,TaskQueue.getQueue(), TaskMap.getFutureMap(), BuffMap.getBuffMap());
        //添加任务到队列
        mostRole.getQueue().add(task);
        //线程执行任务
        MapThreadPool.ACCOUNT_SERVICE[threadIndex].scheduleAtFixedRate( () ->{
                    Iterator<Runnable> iterator = mostRole.getQueue().iterator();
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
//            int bigger = Math.max(roleList.get(i).getOccupation().getAttract(),roleList.get(i-1).getOccupation().getAttract());
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

    /**
     * 初始化任务
     * @param role 角色
     * @param queue 任务队列
     * @param map 地图
     * @param buffMap buffMap
     */
    public static void initRoleTask(ConcreteRole role, Queue<Runnable> queue, Map<String, Future> map, Map<String,ConcreteBuff> buffMap){
        role.setMapBuff(buffMap);
        role.setQueue(queue);
        role.setTaskMap(map);
    }

    /**
     * 创建队伍
     * @param channel channel
     * @param requestBossInfo requestBossInfo
     * @return 协议信息
     */
    public MsgBossInfoProto.ResponseBossInfo createTeam(Channel channel, MsgBossInfoProto.RequestBossInfo requestBossInfo) {
        //getRole
        ConcreteRole role = getRole(channel);
        //getTeamName
        String teamName = requestBossInfo.getTeamName();
        //roleTeamMap
        Map<String, String> roleTeamMap = TeamMapManager.getRoleTeamMap();
        roleTeamMap.put(role.getName(),teamName);
        //addTeam
        Map<String, List<ConcreteRole>> teamMap = TeamMapManager.getTeamMap();
        //list
        List<ConcreteRole> roleList = new ArrayList<>();
        roleList.add(role);
        teamMap.put(teamName,roleList);
        //content
        String content = role.getName()+"成功创建队伍:"+teamName;
        return MsgBossInfoProto.ResponseBossInfo.newBuilder()
                .setType(MsgBossInfoProto.RequestType.CREATETEAM)
                .setContent(content)
                .build();
    }
    /**
     * 加入队伍
     * @param channel channel
     * @param requestBossInfo requestBossInfo
     * @return 协议信息
     */
    public MsgBossInfoProto.ResponseBossInfo joinTeam(Channel channel, MsgBossInfoProto.RequestBossInfo requestBossInfo) {
        //getRole
        ConcreteRole role = getRole(channel);
        //getTeamName
        String teamName = requestBossInfo.getTeamName();
        //addTeam
        Map<String, List<ConcreteRole>> teamMap = TeamMapManager.getTeamMap();
        //list
        List<ConcreteRole> roleList = teamMap.get(teamName);
        //join
        roleList.add(role);
        //content
        String content = role.getName()+"成功加入队伍:"+teamName;
        return MsgBossInfoProto.ResponseBossInfo.newBuilder()
                .setType(MsgBossInfoProto.RequestType.JOINTEAM)
                .setContent(content)
                .build();
    }
    /**
     * 退出队伍
     * @param channel channel
     * @param requestBossInfo requestBossInfo
     * @return 协议信息
     */
    public MsgBossInfoProto.ResponseBossInfo exitTeam(Channel channel, MsgBossInfoProto.RequestBossInfo requestBossInfo) {
        //role
        ConcreteRole role = getRole(channel);
        //teamName
        String teamName = requestBossInfo.getTeamName();
        //exitTeam
        Map<String, List<ConcreteRole>> teamMap = TeamMapManager.getTeamMap();
        List<ConcreteRole> roleList = teamMap.get(teamName);
        //remove
        roleList.remove(role);
        //content
        String content = role.getName()+"成功离开队伍:"+teamName;
        return MsgBossInfoProto.ResponseBossInfo.newBuilder()
                .setType(MsgBossInfoProto.RequestType.EXITTEAM)
                .setContent(content)
                .build();
    }

    /**
     * 解散队伍
     * @param channel channel
     * @param requestBossInfo requestBossInfo
     * @return 协议信息
     */
    public MsgBossInfoProto.ResponseBossInfo dismissTeam(Channel channel, MsgBossInfoProto.RequestBossInfo requestBossInfo) {
        //role
        ConcreteRole role = getRole(channel);
        //teamName
        String teamName = requestBossInfo.getTeamName();
        //dismissTeam
        Map<String, List<ConcreteRole>> teamMap = TeamMapManager.getTeamMap();
        //roleTeamMap
        Map<String, String> roleTeamMap = TeamMapManager.getRoleTeamMap();
        List<ConcreteRole> roleList = teamMap.get(teamName);
        //content
        String content = null;
        if(roleList.get(0).getName().equals(role.getName())){
            content = role.getName()+"成功解散队伍";
            roleList.clear();
            teamMap.remove(teamName);
            roleTeamMap.remove(role.getName());
        }else{
            content = role.getName()+"不是队长，无法解散队伍";
        }
        //return
        return MsgBossInfoProto.ResponseBossInfo.newBuilder()
                .setType(MsgBossInfoProto.RequestType.DISMISSTEAM)
                .setContent(content)
                .build();
    }
    /**
     * 查询队伍
     * @param channel channel
     * @param requestBossInfo requestBossInfo
     * @return 协议信息
     */
    public MsgBossInfoProto.ResponseBossInfo queryTeam(Channel channel, MsgBossInfoProto.RequestBossInfo requestBossInfo) {
        //content
        StringBuilder content = new StringBuilder();
        content.append("队伍名称\t队员\n");
        //dismissTeam
        Map<String, List<ConcreteRole>> teamMap = TeamMapManager.getTeamMap();
        Set<Map.Entry<String, List<ConcreteRole>>> entries = teamMap.entrySet();
        for (Map.Entry<String, List<ConcreteRole>> entry : entries) {
            content.append(" "+entry.getKey()+"\t");
            List<ConcreteRole> roleList = entry.getValue();
            for (ConcreteRole role : roleList) {
                content.append(role.getName()+"\t");
            }
            content.append("\n");
        }
        //return
        return MsgBossInfoProto.ResponseBossInfo.newBuilder()
                .setType(MsgBossInfoProto.RequestType.QUERYTEAM)
                .setContent(content.toString())
                .build();
    }


}
