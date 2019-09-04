package com.game.skill.service;

import com.game.backpack.bean.GoodsResource;
import com.game.backpack.service.BackpackService;
import com.game.event.beanevent.MonsterDeadEvent;
import com.game.event.manager.EventMap;
import com.game.map.bean.ConcreteMap;
import com.game.notice.NoticeUtils;
import com.game.npc.bean.ConcreteMonster;
import com.game.npc.bean.MonsterMapMapping;
import com.game.property.bean.PropertyType;
import com.game.protobuf.protoc.MsgSkillInfoProto;
import com.game.role.bean.ConcreteRole;
import com.game.role.manager.InjectRoleProperty;
import com.game.role.service.RoleService;
import com.game.skill.bean.ConcreteSkill;
import com.game.user.manager.LocalUserMap;
import com.game.utils.CacheUtils;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @ClassName SkillService
 * @Description 技能服务
 * @Author DELL
 * @Date 2019/8/12 15:46
 * @Version 1.0
 */
@Service
public class SkillService {
    /**
     * 背包服务
     */
    @Autowired
    private BackpackService backpackService;
    /**
     * 角色服务
     */
    @Autowired
    private RoleService roleService;
    /**
     * 怪兽死亡事件
     */
    @Autowired
    private MonsterDeadEvent monsterDeadEvent;
    /**
     * 事件map
     */
    @Autowired
    private EventMap eventMap;
    /**
     * 升级技能
     * @param channel channel
     * @param requestSkillInfo 技能请求信息
     * @return 协议信息
     */
    public MsgSkillInfoProto.ResponseSkillInfo upgradeSkill(Channel channel, MsgSkillInfoProto.RequestSkillInfo requestSkillInfo) {
       //获取role
        ConcreteRole role = getRole(channel);
        //获取技能名称
        String skillName = requestSkillInfo.getSkillName();
        //判断角色有没有技能包
        GoodsResource consumeGoods = findGoods(role);
        if(consumeGoods==null){
            //内容
            String content =  role.getName()+"没技能包";
            //返回消息
            return MsgSkillInfoProto.ResponseSkillInfo.newBuilder()
                    .setContent(content)
                    .setType(MsgSkillInfoProto.RequestType.UPGRADESKILL)
                    .build();
        }
        //获取技能(from db)
        ConcreteSkill skill = role.getConcreteSkill();
        //内容
        String content =  handleSkill(skill,skillName,role,consumeGoods);
        //返回消息
        return MsgSkillInfoProto.ResponseSkillInfo.newBuilder()
                .setContent(content)
                .setType(MsgSkillInfoProto.RequestType.UPGRADESKILL)
                .build();
    }

    /**
     * 技能逻辑处理
     * @param skill Skill
     * @param skillName 技能名
     * @param role 角色
     * @param consumeGoods 消耗物品
     * @return 消息
     */
    private String handleSkill(ConcreteSkill skill, String skillName, ConcreteRole role, GoodsResource consumeGoods) {
        if(skill!=null){
            //skill:1,2,3
            final String[] skills = skill.getId().split(",");
            //找出具体技能且升级
            return handleSkills(skills,skillName,role,consumeGoods);
        }else{
            //报错，返回信息
            return role.getName()+"没技能:"+skillName+";如需升级技能，请先学习技能";
        }
    }

    /**
     * 处理技能
     * @param skills 技能组
     * @param skillName 技能名
     * @param role 角色
     * @param consumeGoods 消耗物品
     * @return 协议消息
     */
    private String handleSkills(String[] skills, String skillName, ConcreteRole role, GoodsResource consumeGoods) {
        ConcreteSkill lowSkill = findSkill(skills,skillName);
        if(lowSkill!=null){
            //升级技能
            return upSkill(lowSkill,role,skillName,consumeGoods);
        }else{
            return role.getName()+"没技能:"+skillName+";如需升级技能，请先学习技能";
        }
    }
    /**
     * 升级技能
     * @param lowSkill 技能
     * @param role 角色
     * @param skillName 技能名称
     * @param consumeGoods 消耗物品
     * @return 协议消息
     */
    private String upSkill(ConcreteSkill lowSkill, ConcreteRole role, String skillName, GoodsResource consumeGoods) {
        if(lowSkill.getLevel()==2){
            return role.getName()+"的"+skillName+"技能等级已满";
        }
        ConcreteSkill highSkill = CacheUtils.getSkillMapKeyName().get(lowSkill.getName() + (lowSkill.getLevel()+1));
        //修改技能id
        updateSkillId(role,highSkill);
        role.setConcreteSkill(highSkill);
        roleService.updateRole(role);
        //消耗一本技能包
        backpackService.updateGoodsByRoleIdDel(role.getId(),consumeGoods.getId());
        return role.getName()+"成功升级技能："+skillName;
    }
    /**
     * 修改技能id
     * @param concreteRole role
     * @param concreteSkill skill
     */
    private void updateSkillId(ConcreteRole concreteRole, ConcreteSkill concreteSkill) {
        //oldId的数据格式：1,2
        StringBuffer oldId = new StringBuffer(concreteRole.getConcreteSkill().getId());
        //拼接字符串
        StringBuffer newId = new StringBuffer();
        newId.append(oldId).append(",").append(concreteSkill.getId());
        concreteSkill.setId(newId.toString());
    }
    /**
     * 找技能
     * @param skills skills
     * @param skillName skill's name
     * @return skill
     */
    private ConcreteSkill findSkill(String[] skills,String skillName) {
        ConcreteSkill lowSkill = null;
        //有技能时，不能学习已有技能
        for (int i = 0; i < skills.length; i++) {
            CacheUtils.printMap(CacheUtils.getSkillMapKeyId());
            ConcreteSkill concreteSkill = CacheUtils.getSkillMapKeyId().get(skills[i]);
            if(Objects.equals(concreteSkill.getName(),skillName)){
                lowSkill = concreteSkill;
            }
        }
        return lowSkill;
    }
    /**
     * 找技能包
     * @param role role
     * @return goods
     */
    private GoodsResource findGoods(ConcreteRole role) {
        List<GoodsResource> goods = backpackService.getGoodsByRoleId(role.getId());
        GoodsResource consumeGoods = null;
        for (int i = 0; i < goods.size(); i++) {
            if (goods.get(i).getName().equals("技能包")) {
                consumeGoods = goods.get(i);
            }
        }
        return consumeGoods;
    }

    /**
     * 学习技能
     * @param channel channel
     * @param requestSkillInfo 技能请求信息
     * @return 协议信息
     */
    public MsgSkillInfoProto.ResponseSkillInfo studySkill(Channel channel, MsgSkillInfoProto.RequestSkillInfo requestSkillInfo) {
        //获取role
        ConcreteRole role = getRole(channel);
        //获取技能
        String skillName = requestSkillInfo.getSkillName();
        String content = checkAndLearn(role,skillName);
        return MsgSkillInfoProto.ResponseSkillInfo.newBuilder()
                .setType(MsgSkillInfoProto.RequestType.STUDYSKILL)
                .setContent(content)
                .build();
    }
    /**
     * 判断角色是否已经拥有该技能或判断角色是否达到学习该技能的条件
     * @param concreteRole role
     * @param skillname skill's name
     * @return 协议信息
     */
    private String checkAndLearn(ConcreteRole concreteRole, String skillname) {
        String rolename = concreteRole.getName();
        ConcreteSkill skill = concreteRole.getConcreteSkill();
        if(skill==null){
            //该角色无技能，学习技能
            ConcreteSkill concreteSkill = CacheUtils.getSkillMapKeyName().get(skillname+1);
            concreteRole.setConcreteSkill(concreteSkill);
            //更新数据库
            roleService.updateRole(concreteRole);
            return rolename+"成功学习技能："+skillname;
        }
        //skills实际是skill的id，数据类型是varchar;格式为1,2,3
        final String[] skills = skill.getId().split(",");
        boolean learned = false;
        //有技能时，不能学习已有技能
        for (int i = 0; i < skills.length; i++) {
            CacheUtils.printMap(CacheUtils.getSkillMapKeyId());
            ConcreteSkill concreteSkill = CacheUtils.getSkillMapKeyId().get(skills[i]);
            if(Objects.equals(concreteSkill.getName(),skillname)){
                learned = true;
            }
        }
        if(learned){
            return "已经学了技能："+skillname+",不能再学了！！";
        }else{
            ConcreteSkill concreteSkill = CacheUtils.getSkillMapKeyName().get(skillname+1);
            //修改技能id
            updateSkillId(concreteRole,concreteSkill);
            concreteRole.setConcreteSkill(concreteSkill);
            //更新角色
            roleService.updateRole(concreteRole);
            //返回消息
            return rolename+"成功学习技能："+skillname;
        }
    }

    /**
     * 角色PK
     * @param channel channel
     * @param requestSkillInfo 协议请求信息
     * @return 协议信息
     */
    public MsgSkillInfoProto.ResponseSkillInfo rolePK(Channel channel, MsgSkillInfoProto.RequestSkillInfo requestSkillInfo) {
        ConcreteRole attackRole = getRole(channel);
        //获取角色--->获取地图id--->获取地图上的角色列表
        List<ConcreteRole> roleList = prepareForPk(attackRole.getName());
        //target
        String target = requestSkillInfo.getTarget();
        //skill
        String skillName = requestSkillInfo.getSkillName();
        //找出具体的
        ConcreteRole attackedRole = findRole(roleList,target);
        //获取技能---使用技能---判断是否具备攻击条件--攻击--返回信息
        String content = attackPk(attackedRole,skillName,attackRole.getName(),target);
        return MsgSkillInfoProto.ResponseSkillInfo.newBuilder()
                .setContent(content)
                .setType(MsgSkillInfoProto.RequestType.ROLEPK)
                .build();
    }

    /**
     * 攻击
     * @param attackedRole 角色
     * @param skillName 技能名称
     * @param roleName 角色名称
     * @param targetRoleName 目标对象
     * @return 协议消息
     */
    private String attackPk(ConcreteRole attackedRole, String skillName, String roleName, String targetRoleName) {
        //从local获取本地角色
        ConcreteRole attackRole = CacheUtils.getMapRoleNameRole().get(roleName);
        //检查当期地图是否存在怪兽
        if(attackedRole==null){
            return "地图："+attackRole.getConcreteMap().getName()+"没角色:"+targetRoleName;
        }
        //获取技能和使用技能
        ConcreteSkill skill = attackRole.getConcreteSkill();
        String[] skills = skill.getId().split(",");
        //在技能集合中选出指定技能
        ConcreteSkill concreteSkill = findSkill(skills, skillName);

        //角色剩下的mp值
        int leftMp = attackRole.getCurMp();

        //技能需要消耗的mp值
        Integer costMp = concreteSkill.getMp();
        //判断是否具备释放技能的条件
        if(costMp>leftMp){
            return "角色的mp值不够c释放节能"+"角色mp值:"+leftMp+"\t"+"技能需消耗的mp值:"+costMp;
        }
        //技能的伤害值
        Integer hurt = concreteSkill.getHurt();
        //玩家的自身攻击力
        Integer attack = attackRole.getAttack();
        //总攻击力
        Integer totalAttack = hurt + attack;
        //技能消耗角色的mp值(更新属性系统和通知角色更新属性)
        attackRole.getCurMap().put(PropertyType.MP,leftMp-costMp);
        InjectRoleProperty.injectRoleProperty(attackRole);
        //获取怪兽的生命值
        int curHp = attackedRole.getCurHp();
        //怪兽的生命值减少并通知角色属性更新
        Map<PropertyType, Integer> curMap = attackedRole.getCurMap();
        //被攻击的角色生命值减少，更新属性模块
        curMap.put(PropertyType.HP,curHp-totalAttack);
        InjectRoleProperty.injectRoleProperty(attackedRole);

        return roleName+"成功攻击"+targetRoleName+"\n("+roleName+"的mp值从"+leftMp+"变为"+attackRole.getCurMp()+
                ";"+targetRoleName+"的hp值从"+curHp+"变为"+attackedRole.getCurHp()+")";
    }
    /**
     * 找具体的角色
     * @param roleList roleList
     * @param targetRoleName 目标对象
     * @return role
     */
    private ConcreteRole findRole(List<ConcreteRole> roleList, String targetRoleName) {
        for (int i = 0; i < roleList.size(); i++) {
            if (Objects.equals(roleList.get(i).getName(),targetRoleName)) {
                return roleList.get(i);
            }
        }
        return null;
    }
    /**
     * 准备Pk
     * @param roleName 角色名
     * @return 角色列表
     */
    private List<ConcreteRole> prepareForPk(String roleName) {
        //获取角色
        ConcreteRole concreteRole = getRoleFromDB(roleName);
        //获取地图id
        int mapId = concreteRole.getConcreteMap().getId();
        //创建链表
        List<ConcreteRole> roleList = new ArrayList<>();
        //根据角色id来找出其他角色
        Map<String, ConcreteRole> roleMap = CacheUtils.getMapRoleNameRole();
        Set<Map.Entry<String, ConcreteRole>> entrySet = roleMap.entrySet();
        Iterator<Map.Entry<String, ConcreteRole>> iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, ConcreteRole> next = iterator.next();
            ConcreteRole role = next.getValue();
            if(mapId==role.getConcreteMap().getId()){
                roleList.add(role);
            }
        }
        return roleList;
    }
    /**
     * 从数据库获取角色
     * @param roleName 角色名
     * @return role
     */
    public ConcreteRole getRoleFromDB(String roleName){
        return roleService.getRoleByRoleName(roleName);
    }
    /**
     * 使用技能
     * @param channel
     * @param requestSkillInfo 技能请求信息
     * @return 协议信息
     */
    public MsgSkillInfoProto.ResponseSkillInfo useSkill(Channel channel, MsgSkillInfoProto.RequestSkillInfo requestSkillInfo) {
        ConcreteRole role = getRole(channel);
        //获取角色--->获取地图id--->获取地图上的怪兽列表
        List<Integer> monsterList =  prepareForAttack(role.getName());
        //target
        String target = requestSkillInfo.getTarget();
        //skill
        String skillName = requestSkillInfo.getSkillName();
        //找出具体怪兽
        ConcreteMonster monster = findConcreteMonster(monsterList,target);
        String content = null;
        if(monster.getHp()<=0){
            content = monster.getName()+"已死，不能再攻击";
        }else {
            content = attack(monster,skillName,role.getName(),target,null);
        }
        //获取技能---使用技能---判断是否具备攻击条件--攻击--返回信息
        return MsgSkillInfoProto.ResponseSkillInfo.newBuilder()
                .setType(MsgSkillInfoProto.RequestType.USESKILL)
                .setContent(content)
                .build();
    }

    /**
     * 攻击怪兽
     * @param role 角色
     * @param monster 怪兽
     * @param skillName 技能名
     * @return 协议信息
     */
    public void useSkill(ConcreteRole role, ConcreteMonster monster, String skillName, ConcreteMap map){
        String content = null;
        if(monster.getHp()<=0){
            content = monster.getName()+"已死，不能再攻击";
        }else {
            content = attack(monster,skillName,role.getName(),monster.getName(),map);
        }
        ConcreteRole tmpRole = CacheUtils.getMapRoleNameRole().get(role.getName());
        //获取技能---使用技能---判断是否具备攻击条件--攻击--返回信息
        MsgSkillInfoProto.ResponseSkillInfo skillInfo = MsgSkillInfoProto.ResponseSkillInfo.newBuilder()
                .setType(MsgSkillInfoProto.RequestType.USESKILL)
                .setContent(content)
                .build();
        //返回消息
        tmpRole.getChannel().writeAndFlush(skillInfo);
    }
    /**
     * 攻击
     * @param monster 怪兽
     * @param skillName 技能名
     * @param roleName 角色名
     * @param monsterName 怪兽名
     * @return 协议信息
     */
    private String attack(ConcreteMonster monster,String skillName,String roleName,String monsterName,ConcreteMap map) {
        //从local获取本地角色
        ConcreteRole localRole = CacheUtils.getMapRoleNameRole().get(roleName);
        //检查当期地图是否存在怪兽
        if(monster==null){
            return "地图："+localRole.getConcreteMap().getName()+"没怪兽:"+monsterName;
        }
        //获取技能和使用技能
        ConcreteSkill skill = localRole.getConcreteSkill();
        String[] skills = skill.getId().split(",");
        //在技能集合中选出指定技能
        ConcreteSkill concreteSkill = findSkill(skills, skillName);
        //角色剩下的mp值
        int leftMp = localRole.getCurMp();
        //获取怪兽的生命值
        Integer monsterHp = monster.getHp();
        //技能为空，使用普通攻击
        if(concreteSkill==null){
            //玩家的自身攻击力
            Integer attack = localRole.getAttack();
            //怪兽的生命值减少
            monster.setHp(monster.getHp()-attack);
        }else {
            //技能需要消耗的mp值
            Integer costMp = concreteSkill.getMp();
            //判断是否具备释放技能的条件
            if(costMp>leftMp){
                return "角色的mp值不够c释放节能"+"角色mp值:"+leftMp+"\t"+"技能需消耗的mp值:"+costMp;
            }
            //技能的伤害值
            Integer hurt = concreteSkill.getHurt();
            //玩家的自身攻击力
            Integer attack = localRole.getAttack();
            //总攻击力
            Integer totalAttack = hurt + attack;
            //技能消耗角色的mp值(更新属性系统和通知角色更新属性)
            localRole.getCurMap().put(PropertyType.MP,leftMp-costMp);
            InjectRoleProperty.injectRoleProperty(localRole);
            //怪兽的生命值减少
            monster.setHp(monster.getHp()-totalAttack);
        }
        //怪兽死亡，通知该地图所有玩家
        if(monster.getHp()<=0){
            //通知
            NoticeUtils.notifyAllRoles(monster);
            //怪兽死亡事件
            monsterDeadEvent.setRole(localRole);
            monsterDeadEvent.setMonster(monster);
            //触发事件，记录怪兽死亡次数
            eventMap.submit(monsterDeadEvent);
            //掉落装备
            falldownEquip(localRole);
            //销毁副本
            map = null;
            //Move to 村子
        }
        //返回消息
        return roleName+"成功攻击"+monsterName+"\n("+roleName+"的mp值从"+leftMp+"变为"+localRole.getCurMp()+
                ";"+monsterName+"的hp值从"+monsterHp+"变为"+monster.getHp()+")";
    }

    public void falldownEquip(ConcreteRole localRole) {
        List<GoodsResource> goodsList = CacheUtils.getGoodsList();
        //掉落装备数量
        int num = (int)((Math.random())*goodsList.size()+1);

        for(int i=0;i<num;i++){
            //掉落装备id
            int goodsId = (int)(Math.random()*goodsList.size());
            //获取装备
            GoodsResource goods = goodsList.get(goodsId);

            //拾取装备
            backpackService.getGoods(localRole.getChannel(),goods.getName());
        }
    }

    /**
     * 找出具体的怪兽
     * @param monsterList 怪兽列表
     * @param monsterName 怪兽名字
     * @return monster
     */
    public ConcreteMonster findConcreteMonster(List<Integer> monsterList,String monsterName) {
        ConcreteMonster monster = null;
        for (int i = 0; i < monsterList.size(); i++) {
            if (Objects.equals(CacheUtils.getMonsterMap().get(monsterList.get(i)).getName(),monsterName)) {
                return CacheUtils.getMonsterMap().get(monsterList.get(i));
            }
        }
        return null;
    }
    /**
     * 准备攻击
     * @param roleName 角色名
     * @return
     */
    public List<Integer> prepareForAttack(String roleName) {
        //获取角色
        ConcreteRole concreteRole = getRoleFromDB(roleName);
        //获取地图id
        int mapId = concreteRole.getConcreteMap().getId();
       //找出怪兽列表
        return findMonster(mapId);
    }

    /**
     * 找怪兽
     * @param mapId 地图id
     * @return monster
     */
    public List<Integer> findMonster(int mapId){
        List<Integer> monsterList = new ArrayList<>();
        //地图和怪兽的映射列表
        List<MonsterMapMapping> monsterMapMappingList = CacheUtils.getMonsterMapMappingList();
        //遍历找出具体的怪兽列表
        for (int i = 0; i < monsterMapMappingList.size(); i++) {
            if(monsterMapMappingList.get(i).getMapId()==mapId){
                monsterList.add(monsterMapMappingList.get(i).getMonsterId());
            }
        }
        return monsterList;
    }

    /**
     * 获取角色
     * @param channel channel
     * @return role
     */
    public ConcreteRole getRole(Channel channel){
        //获取userid
        Integer userId = LocalUserMap.getChannelUserMap().get(channel);
        //获取role
        ConcreteRole role = LocalUserMap.getUserRoleMap().get(userId);
        return role;
    }

    public void useSkillBaby(ConcreteRole role, ConcreteMonster attackedBoss, ConcreteMap map) {
        String content = null;
        if(attackedBoss.getHp()<=0){
            content = attackedBoss.getName()+"已死，不能再攻击";
        }else {
            content = babyAttack(attackedBoss,role,map);
        }
        ConcreteRole tmpRole = CacheUtils.getMapRoleNameRole().get(role.getName());
        //获取技能---使用技能---判断是否具备攻击条件--攻击--返回信息
        MsgSkillInfoProto.ResponseSkillInfo skillInfo = MsgSkillInfoProto.ResponseSkillInfo.newBuilder()
                .setType(MsgSkillInfoProto.RequestType.USESKILL)
                .setContent(content)
                .build();
        //返回消息
        tmpRole.getChannel().writeAndFlush(skillInfo);
    }

    /**
     * 宝宝攻击怪兽
     * @param monster 怪兽
     * @param role role
     * @param map map
     * @return 协议信息
     */
    private String babyAttack(ConcreteMonster monster, ConcreteRole role, ConcreteMap map) {
        //获取monster的当前血量
        Integer monsterHp = monster.getHp();
        //技能伤害
        ConcreteSkill skill = role.getConcreteSkill();
        Integer hurt = skill.getHurt();

        monster.setHp(monsterHp-hurt);
        //怪兽死亡，通知该地图所有玩家
        if(monster.getHp()<=0){
            adviceRole(monster,role,map);
        }
        String roleName = role.getName();
        String monsterName = monster.getName();
        //返回消息
        return roleName+"的宝宝成功攻击"+monsterName;
    }

    /**
     * 通知玩家
     */
    public void adviceRole(ConcreteMonster monster,ConcreteRole role,ConcreteMap map) {
        //通知
        NoticeUtils.notifyAllRoles(monster);
        //怪兽死亡事件
        monsterDeadEvent.setRole(role);
        monsterDeadEvent.setMonster(monster);
        //触发事件，记录怪兽死亡次数
        eventMap.submit(monsterDeadEvent);
        //掉落装备
        falldownEquip(role);
        //销毁副本
        map = null;
        //Move to 村子
    }


}
