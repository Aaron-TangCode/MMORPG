package com.game.duplicate.task;

import com.game.map.bean.ConcreteMap;
import com.game.npc.bean.ConcreteMonster;
import com.game.role.bean.ConcreteRole;
import com.game.skill.service.SkillService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName RoleAttackBossTask
 * @Description 角色攻击boss
 * @Author DELL
 * @Date 2019/8/23 16:29
 * @Version 1.0
 */
public class RoleAttackBossTask implements Runnable {
    private List<ConcreteRole> roleList;

    private Map<Integer, ConcreteMonster> monsterMap;

    private SkillService skillService;
    private ConcreteMap map;

    public RoleAttackBossTask(List<ConcreteRole> roleList, Map<Integer, ConcreteMonster> monsterMap, SkillService skillService, ConcreteMap map) {
        this.roleList = roleList;
        this.monsterMap = monsterMap;
        this.skillService = skillService;
        this.map = map;
    }

    @Override
    public void run() {
        System.out.println("===========");
        //role attack boss
        for(int i=0;i<roleList.size();i++){
            ConcreteRole role = roleList.get(i);
            attackBoss(role,monsterMap);
        }
    }

    /**
     * role attack monster randomly
     * @param role role
     * @param monsterMap bossMap
     */
    private void attackBoss(ConcreteRole role, Map<Integer, ConcreteMonster> monsterMap) {
        //list
        List<ConcreteMonster> monsterList = new ArrayList<>();
        //iterator
        Set<Map.Entry<Integer, ConcreteMonster>> entries = monsterMap.entrySet();
        //把怪兽存进List
        for (Map.Entry<Integer, ConcreteMonster> entry : entries) {
            ConcreteMonster monster = entry.getValue();
            monsterList.add(monster);
        }
        //随机编号
        int ram = (int)(Math.random()*monsterList.size());
        //随机怪兽
        ConcreteMonster attackedBoss = monsterList.get(ram);
        //普通攻击
        skillService.useSkill(role,attackedBoss,null,map);
    }
}
