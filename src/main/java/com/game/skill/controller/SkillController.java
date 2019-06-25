package com.game.skill.controller;

import com.game.backpack.bean.Goods;
import com.game.backpack.service.BackpackService;
import com.game.dispatcher.RequestAnnotation;
import com.game.notice.NoticeUtils;
import com.game.npc.bean.ConcreteMonster;
import com.game.npc.bean.MonsterMapMapping;
import com.game.role.bean.ConcreteRole;
import com.game.role.service.RoleService;
import com.game.skill.bean.ConcreteSkill;
import com.game.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName SkillController
 * @Description 技能控制器
 * @Author DELL
 * @Date 2019/6/13 11:35
 * @Version 1.0
 */
@Component
@RequestAnnotation("/skill")
public class SkillController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private BackpackService backpackService;
    /**
     * 升级技能
     * @param roleName
     * @param skillName
     * @return
     */
    @RequestAnnotation("/upgradeskill")
    public String upgradeSkill(String roleName,String skillName){
        //判断角色有没有技能包
        ConcreteRole role = getRole(roleName);
        Goods consumeGoods = findGoods(role);
        if(consumeGoods==null){
            return roleName+"没技能包";
        }
        //获取技能(from db)
        ConcreteSkill skill = role.getConcreteSkill();
       return handleSkill(skill,skillName,role,consumeGoods);
    }

    private String handleSkill(ConcreteSkill skill,String skillName,ConcreteRole role,Goods consumeGoods) {
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

    private String handleSkills(String[] skills, String skillName,ConcreteRole role,Goods consumeGoods) {
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
     * @param lowSkill
     * @param role
     * @param skillName
     * @param consumeGoods
     * @return
     */
    private String upSkill(ConcreteSkill lowSkill,ConcreteRole role,String skillName,Goods consumeGoods) {
        if(lowSkill.getLevel()==2){
            return role.getName()+"的"+skillName+"技能等级已满";
        }
        ConcreteSkill highSkill = MapUtils.getSkillMap_keyName().get(lowSkill.getName() + (lowSkill.getLevel()+1));
        //修改技能id
        updateSkillId(role,highSkill);
        role.setConcreteSkill(highSkill);
        roleService.updateRole(role);
        //消耗一本技能包
        backpackService.updateGoodsByRoleIdDel(role.getId(),consumeGoods.getId());
        return role.getName()+"成功升级技能："+skillName;
    }



    /**
     * 找技能包
     * @param role
     * @return
     */
    private Goods findGoods(ConcreteRole role) {
        List<Goods> goods = backpackService.getGoodsByRoleId(role.getId());
        Goods consumeGoods = null;
        for (int i = 0; i < goods.size(); i++) {
            if (goods.get(i).getName().equals("技能包")) {
                consumeGoods = goods.get(i);
            }
        }
        return consumeGoods;
    }


    @RequestAnnotation("/studyskill")
    public String studySkill(String roleName,String skillName){
        //获取角色
        ConcreteRole concreteRole = getRole(roleName);
        //判断角色是否已经拥有该技能或判断角色是否达到学习该技能的条件
        return checkAndLearn(concreteRole,skillName);
    }

    /**
     * 判断角色是否已经拥有该技能或判断角色是否达到学习该技能的条件
     * @param concreteRole
     * @param skillname
     * @return
     */
    private String checkAndLearn(ConcreteRole concreteRole, String skillname) {
        String rolename = concreteRole.getName();
        ConcreteSkill skill = concreteRole.getConcreteSkill();
        if(skill==null){
            //该角色无技能，学习技能
            ConcreteSkill concreteSkill = MapUtils.getSkillMap_keyName().get(skillname);
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
            MapUtils.printMap(MapUtils.getSkillMap_keyId());
            ConcreteSkill concreteSkill = MapUtils.getSkillMap_keyId().get(skills[i]);
            if(Objects.equals(concreteSkill.getName(),skillname)){
                learned = true;
            }
        }
        if(learned){
            return "已经学了技能："+skillname+",不能再学了！！";
        }else{
            ConcreteSkill concreteSkill = MapUtils.getSkillMap_keyName().get(skillname);
            //修改技能id
            updateSkillId(concreteRole,concreteSkill);
            concreteRole.setConcreteSkill(concreteSkill);

            roleService.updateRole(concreteRole);
            return rolename+"成功学习技能："+skillname;
        }
    }

    /**
     * 修改技能id
     * @param concreteRole
     * @param concreteSkill
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
     * 使用技能（伤害单个怪兽）
     * @return
     */
    @RequestAnnotation("/useskill")
    public String useSkill(String roleName,String skillName,String monsterName){
        //获取角色--->获取地图id--->获取地图上的怪兽列表
        List<Integer> monsterList =  prepareForAttack(roleName);
        //找出具体怪兽
        ConcreteMonster monster = findConcreteMonster(monsterList,monsterName);
        //获取技能---使用技能---判断是否具备攻击条件--攻击--返回信息
        String msg = attack(monster,skillName,roleName,monsterName);
       return msg;
    }

    /**
     * 找出具体的怪兽
     * @param monsterList
     * @param monsterName
     * @return
     */
    private ConcreteMonster findConcreteMonster(List<Integer> monsterList,String monsterName) {
        ConcreteMonster monster = null;
        for (int i = 0; i < monsterList.size(); i++) {

            if (Objects.equals(MapUtils.getMonsterMap().get(monsterList.get(i)).getName(),monsterName)) {
                return MapUtils.getMonsterMap().get(monsterList.get(i));
            }
        }
      return null;
    }

    /**
     * 攻击
     * @param monster
     * @param skillName
     * @param roleName
     * @param monsterName
     * @return
     */
    private String attack(ConcreteMonster monster,String skillName,String roleName,String monsterName) {
        ConcreteRole concreteRole = roleService.getRoleByRoleName(roleName);
        if(monster==null){
            return "地图："+concreteRole.getConcreteMap().getName()+"没怪兽:"+monsterName;
        }
        //获取技能和使用技能
        ConcreteSkill skill = concreteRole.getConcreteSkill();
        String[] skills = skill.getId().split(",");
        ConcreteSkill concreteSkill = findSkill(skills, skillName);

        //角色剩下的mp值
        int leftMp = concreteRole.getCurMp();

        //技能需要消耗的mp值
        Integer costMp = concreteSkill.getMp();
        //判断是否具备释放技能的条件
        if(costMp>leftMp){
            return "角色的mp值不够c释放节能"+"角色mp值:"+leftMp+"\t"+"技能需消耗的mp值:"+costMp;
        }
        //技能的伤害值
        Integer hurt = concreteSkill.getHurt();
        //技能消耗角色的mp值
//        Property property = PropertyManager.getMap().get(concreteRole.getLevel());
//        property.setMp(leftMp-costMp);

//        concreteRole.setMp();
        Integer monsterHp = monster.getHp();
        //怪兽的生命值减少
        monster.setHp(monster.getHp()-hurt);
        //怪兽死亡，通知该地图所有玩家
        if(monster.getHp()<=0){
            NoticeUtils.notifyAllRoles(monster);
        }
        return roleName+"成功攻击"+monsterName+"\n("+roleName+"的mp值从"+leftMp+"变为"+concreteRole.getCurMp()+
                ";"+monsterName+"的hp值从"+monsterHp+"变为"+monster.getHp()+")";
    }

    /**
     * 准备攻击
     * @param roleName
     * @return
     */
    private List<Integer> prepareForAttack(String roleName) {
        //获取角色
        ConcreteRole concreteRole = roleService.getRoleByRoleName(roleName);
        //获取地图id
        int mapId = concreteRole.getConcreteMap().getId();
        //获取地图上的怪兽列表
        List<Integer> monsterList = new ArrayList<>();
        List<MonsterMapMapping> monsterMapMappingList = MapUtils.getMonsterMapMappingList();
        for (int i = 0; i < monsterMapMappingList.size(); i++) {
            if(monsterMapMappingList.get(i).getMapId()==mapId){
                monsterList.add(monsterMapMappingList.get(i).getMonsterId());
            }
        }
        return monsterList;
    }

    /**
     * 获取角色
     */
    public ConcreteRole getRole(String roleName){
       return roleService.getRoleByRoleName(roleName);
    }

    /**
     * 找技能
     * @param skills
     * @param skillName
     * @return
     */
    private ConcreteSkill findSkill(String[] skills,String skillName) {
        ConcreteSkill lowSkill = null;
        //有技能时，不能学习已有技能
        for (int i = 0; i < skills.length; i++) {
            MapUtils.printMap(MapUtils.getSkillMap_keyId());
            ConcreteSkill concreteSkill = MapUtils.getSkillMap_keyId().get(skills[i]);
            if(Objects.equals(concreteSkill.getName(),skillName)){
                lowSkill = concreteSkill;
            }
        }
        return lowSkill;
    }
}
