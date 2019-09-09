package com.game.duplicate.serivce;

import com.game.buff.bean.ConcreteBuff;
import com.game.duplicate.manager.RoleAndMap;
import com.game.duplicate.task.RoleAttackBossTask;
import com.game.event.beanevent.AttackedEvent;
import com.game.event.beanevent.MonsterDeadEvent;
import com.game.event.manager.EventMap;
import com.game.map.bean.ConcreteMap;
import com.game.map.manager.MapFactory;
import com.game.map.service.MapService;
import com.game.map.task.BossAutoAttackTask;
import com.game.map.threadpool.MapThreadPool;
import com.game.map.threadpool.TaskQueue;
import com.game.notice.NoticeUtils;
import com.game.npc.bean.ConcreteMonster;
import com.game.property.bean.PropertyType;
import com.game.protobuf.protoc.MsgBossInfoProto;
import com.game.role.bean.ConcreteRole;
import com.game.role.manager.InjectRoleProperty;
import com.game.server.manager.BuffMap;
import com.game.server.manager.TaskMap;
import com.game.skill.bean.ConcreteSkill;
import com.game.skill.service.SkillService;
import com.game.team.manager.TeamMapManager;
import com.game.user.manager.LocalUserMap;
import com.game.utils.CacheUtils;
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
     * 怪兽死亡事件
     */
    @Autowired
    private MonsterDeadEvent monsterDeadEvent;
    /**
     * 事件容器
     */
    @Autowired
    private EventMap eventMap;
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
     * 技能service
     */
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
        //getTeam获取team
        Map<String, List<ConcreteRole>> teamMap = TeamMapManager.getTeamMap();
        //roleList角色列表
        List<ConcreteRole> roleList = teamMap.get(teamName);
        //roleNameList,赋值给roleNameList
        List<String> roleNameList = transferList(roleList);
        //mapName地图名称
        String mapName = requestBossInfo.getMapName();

        //初始化角色
        String content = initRole(roleNameList,mapName);
        return MsgBossInfoProto.ResponseBossInfo.newBuilder()
                .setContent(content)
                .setType(MsgBossInfoProto.RequestType.TEAMENTERDUPLICATE)
                .build();
    }

    /**
     * 角色集合-->角色名集合
     * @param roleList 角色集合
     * @return 角色名集合
     */
    private List<String> transferList(List<ConcreteRole> roleList) {
        List<String> roleNameList = new ArrayList<>();
        for (int i = 0; i < roleList.size(); i++) {
            ConcreteRole role = roleList.get(i);
            roleNameList.add(role.getName());
        }
        return roleNameList;
    }

    /**
     * 攻击boss
     * @param channel channel
     * @param requestBossInfo Boss请求信息
     * @return
     */
    public MsgBossInfoProto.ResponseBossInfo enterDuplicate(Channel channel, MsgBossInfoProto.RequestBossInfo requestBossInfo) {
        //role
        ConcreteRole tmpRole = getRole(channel);
        //mapName地图名称
        String mapName = requestBossInfo.getMapName();
        //队伍角色姓名列表
        List<String> roleNameList = new ArrayList<>();
        //把角色添加到队伍列表
        roleNameList.add(tmpRole.getName());
        //初始化角色
        String content = initRole(roleNameList,mapName);
        //返回信息
        return MsgBossInfoProto.ResponseBossInfo.newBuilder()
                .setContent(content)
                .setType(MsgBossInfoProto.RequestType.ENTERDUPLICATE)
                .build();
    }
    /**
     * 副本模块的统一入口，初始化副本
     * @param mapName 地图名字
     * @return 消息
     */
    public String initRole(List<String> roleNameList, String mapName) {
        ConcreteRole captain = CacheUtils.getRole(roleNameList.get(0));
        //移动
        mapService.moveTo(captain.getName(),mapName);
        //获取地图
        ConcreteMap concreteMap = captain.getConcreteMap();
        //获取地图id
        int mapId = concreteMap.getId();
        //获取临时地图
        ConcreteMap tmpMap = mapService.getMap(mapId);
        //新副本
        ConcreteMap map = MapFactory.createMap(tmpMap.getId(),tmpMap.getName());
        //把角色添加到新副本
        for(String roleName:roleNameList){
            //切换地图
            mapService.moveTo(roleName, mapName);
            //获取role
            ConcreteRole role = CacheUtils.getRole(roleName);
            //把角色添加到地图
            map.getRoleList().add(role);
        }

        //获取Boss列表
        List<Integer> list = mapService.findMonster(map.getRoleList().get(0).getConcreteMap().getId());

        //找出地图的boss
        Map<String, ConcreteMonster> bossMap = findConcreteMonster(map, list);
        //boss添加到地图
        map.setMonsterMap(bossMap);
        //玩家自动攻击boss
//        babyAttackBoss(map);
        //boss的自动攻击
        autoAttack(map);
        return  "刷副本";
    }

    /**
     * 找怪兽
     * @param map map地图
     * @param list list
     * @return map容器
     */
    private Map<String,ConcreteMonster> findConcreteMonster(ConcreteMap map, List<Integer> list) {
        Map<String, ConcreteMonster> monsterMap = map.getMonsterMap();
        for (int i = 0; i < list.size(); i++) {
            ConcreteMonster monster = CacheUtils.getMonsterMap().get(list.get(i));
            ConcreteMonster boss = new ConcreteMonster(monster);
            monsterMap.put(boss.getName(),boss);
        }
        roleAndMapRelation(map);

        return monsterMap;
    }

    private void roleAndMapRelation(ConcreteMap map) {
        List<ConcreteRole> roleList = map.getRoleList();
        for (int i = 0; i < roleList.size(); i++) {
            RoleAndMap.getRoleAndMap().put(map.getRoleList().get(i).getName(),map);
        }
    }

    /**
     * 角色攻击怪兽
     * @param map map
     */
    private String babyAttackBoss(ConcreteRole role, ConcreteMap map) {
        //task
        RoleAttackBossTask task = new RoleAttackBossTask(role,skillService,map);
        //选择线程池中的某一个线程处理
        int threadIndex = MapThreadPool.getThreadIndex(map);
        //初始化role和buff
        initRoleTask(role,TaskQueue.getQueue(), TaskMap.getFutureMap(), BuffMap.getBuffMap());
        //添加任务到队列
        role.getQueue().add(task);
        //当前角色蓝量
        int curMp = role.getCurMp();
        //技能消耗蓝量
        ConcreteSkill skill = role.getConcreteSkill();
        Integer costMp = skill.getMp();
        if(costMp>curMp){
            return "蓝不足，无法释放技能";
        }
        //消耗蓝
        role.setCurMp(curMp-costMp);
        //更新
        role.setCurHp(RoleAndMap.varHp);
        //update property
        role.getCurStatMap().put(PropertyType.MP,curMp-costMp);
        role.getCurStatMap().put(PropertyType.HP,RoleAndMap.varHp);
        InjectRoleProperty.injectRoleProperty(role);
        //roleName
        String roleName = role.getName();
        //线程执行任务
        MapThreadPool.ACCOUNT_SERVICE[threadIndex].scheduleAtFixedRate( () ->{
                    Iterator<Runnable> iterator = role.getQueue().iterator();
                    while (iterator.hasNext()) {
                        Runnable runnable = iterator.next();
                        if (Objects.nonNull(runnable)) {
                            runnable.run();
                        }
                    }
                },
                5L,20L, TimeUnit.SECONDS);
        //返回消息
        return roleName+"成功释放技能"+skill.getName()+"\n("+roleName+"的mp值从"+curMp+"变为"+role.getCurMp()+")";

    }

    /**
     * 自动攻击
     * @param map 地图
     */
    public void autoAttack(ConcreteMap map){
        //怪物根据角色的职业的吸引值优先进行攻击
        //遍历角色的仇恨值，选出最大的一个来攻击
        ConcreteRole tmpRole = chooseRole(map);
        ConcreteRole mostRole = CacheUtils.getRole(tmpRole.getName());

        //触发仇恨值最大的角色被攻击事件
        attackedEvent.setRole(mostRole);
        eventMap.submit(attackedEvent);
        //选择线程池中的某一个线程处理
        int threadIndex = MapThreadPool.getThreadIndex(map);
       //bossMap
        Map<String, ConcreteMonster> bossMap = map.getMonsterMap();
        //创建新任务
        BossAutoAttackTask task = new BossAutoAttackTask(mostRole,bossMap,map);
        //初始化role和buff
        initRoleTask(mostRole,TaskQueue.getQueue(), TaskMap.getFutureMap(), BuffMap.getBuffMap());
        //添加任务到队列
        mostRole.getQueue().add(task);

        long initDelay = 10L;
        long period = 20L;
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
                initDelay++,period++, TimeUnit.SECONDS);


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
           resRole =  roleList.get(i);
        }
        return resRole;
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

    /**
     * 使用技能攻击boss
     * @param channel channel
     * @param requestBossInfo 请求信息
     * @return
     */
    public MsgBossInfoProto.ResponseBossInfo useSkillAttackBoss(Channel channel, MsgBossInfoProto.RequestBossInfo requestBossInfo) {
        //role
        ConcreteRole role = getRole(channel);
        //判断角色状态
        if(role.getCurHp()<=0){
            return MsgBossInfoProto.ResponseBossInfo.newBuilder()
                    .setContent("角色死亡了，不能攻击怪兽")
                    .setType(MsgBossInfoProto.RequestType.USESKILLATTACKBOSS)
                    .build();
        }
        //map
        ConcreteMap map = RoleAndMap.getRoleAndMap().get(role.getName());
        //Skill
        String skillName = requestBossInfo.getSkillName();
        ConcreteSkill skill = null;
        if(CacheUtils.getSkillMapKeyName().get(skillName+1)!=null){
            skill = CacheUtils.getSkillMapKeyName().get(skillName+1);
        }
        if(CacheUtils.getSkillMapKeyName().get(skillName+2)!=null){
            skill = CacheUtils.getSkillMapKeyName().get(skillName+2);
        }
        role.setConcreteSkill(skill);

        //monsterName
        String bossName = requestBossInfo.getBossName();
        //monster
        ConcreteMonster boss = map.getMonsterMap().get(bossName);
        //选择要攻击的怪兽
        role.setMonster(boss);
        //return
        String content = null;
        if(boss==null){
            content = "Boss不存在";
        } else if(skill.getName().equals("召唤术")){
            content = babyAttackBoss(role,map);
        }else{
            //角色攻击怪兽
             content = playerAttackBoss(role,map);
        }
        return MsgBossInfoProto.ResponseBossInfo.newBuilder()
                .setContent(content)
                .setType(MsgBossInfoProto.RequestType.USESKILLATTACKBOSS)
                .build();
    }

    /**
     * 角色攻击地图
     * @param role role
     * @param map map
     * @return 协议地图
     */
    private String playerAttackBoss(ConcreteRole role, ConcreteMap map) {
        //boss
        ConcreteMonster boss = role.getMonster();
        //获取角色伤害
        Integer attack = role.getAttack();
        //获取技能伤害
        ConcreteSkill skill = role.getConcreteSkill();
        Integer hurt = skill.getHurt();
        //总伤害
        Integer totalHurt = attack + hurt;
        //消耗蓝量
        Integer costMp = skill.getMp();
        //角色蓝量
        int curMp = role.getCurMp();
        //校验怪兽状态
        boolean bossState = checkBossState(boss);
        if(!bossState){
            return "怪兽已经死亡，不能再攻击,刷副本成功";
        }
        boolean mpState = checkMp(costMp,curMp);
        if(!mpState){
            return "蓝量不足,无法攻击";
        }

        //attack
        Integer bossCurHp = boss.getHp();
        boss.setHp(bossCurHp-totalHurt);
        //mp
        role.setCurMp(curMp-costMp);
        role.setCurHp(RoleAndMap.varHp);
        //update property
        role.getCurStatMap().put(PropertyType.MP,curMp-costMp);
        role.getCurStatMap().put(PropertyType.HP,RoleAndMap.varHp);
        InjectRoleProperty.injectRoleProperty(role);
        //怪兽死亡，通知该地图所有玩家
        if(boss.getHp()<=0){
            //通知
            NoticeUtils.notifyAllRoles(boss);

            //怪兽死亡事件
            monsterDeadEvent.setRole(role);
            monsterDeadEvent.setMonster(boss);
            //触发事件，记录怪兽死亡次数
            eventMap.submit(monsterDeadEvent);

            //掉落装备
            skillService.falldownEquip(role);
            //Move to 村子
//            mapService.moveTo(role.getName(),"村子");
            //销毁副本
            map = null;

        }
        String roleName = role.getName();
        String monsterName = boss.getName();
        //返回消息
        return roleName+"成功攻击"+monsterName+"\n("+roleName+"的mp值从"+curMp+"变为"+role.getCurMp()+
                ";"+monsterName+"的hp值从"+bossCurHp+"变为"+boss.getHp()+")";
    }

    /**
     * 检查蓝量
     * @param costMp 消耗蓝量
     * @param curMp 具备蓝量
     * @return true or false
     */
    private boolean checkMp(Integer costMp, int curMp) {
        return curMp>=costMp?true:false;
    }

    /**
     * 检查Boss状态
     * @param boss boss
     * @return true or false
     */
    private boolean checkBossState(ConcreteMonster boss) {
        return boss.getHp()<=0?false:true;
    }
}
