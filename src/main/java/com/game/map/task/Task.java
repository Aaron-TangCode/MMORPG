package com.game.map.task;

import com.game.map.threadpool.TaskQueue;
import com.game.npc.bean.ConcreteMonster;
import com.game.role.bean.ConcreteRole;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName Task
 * @Description boss攻击角色AI
 * @Author DELL
 * @Date 2019/7/8 16:02
 * @Version 1.0
 */
public class Task implements Runnable{
    private ConcreteRole role;
    private Map<Integer,ConcreteMonster> bossMap;
    public Task(ConcreteRole role, Map<Integer,ConcreteMonster> bossMap) {
        this.role = role;
        this.bossMap = bossMap;
    }

    @Override
    public void run() {
        Set<Map.Entry<Integer, ConcreteMonster>> entries = bossMap.entrySet();
        Iterator<Map.Entry<Integer, ConcreteMonster>> iterator = entries.iterator();
        //遍历地图的每一个boss攻击角色
        while (iterator.hasNext()) {
            Map.Entry<Integer, ConcreteMonster> next = iterator.next();
            ConcreteMonster boss = next.getValue();
            //boss攻击角色
            bossAttack(boss,role);
        }

    }

    private void bossAttack(ConcreteMonster boss, ConcreteRole role) {
        //boss攻击角色
        Integer attack = boss.getAttack();

        int curHp = role.getCurHp();

        role.setCurHp(curHp-attack);

        System.out.println(boss.getName()+"攻击"+role.getName()+"\t角色的血量："+role.getCurHp());

        Integer deadTime = boss.getTime();

        long end = System.currentTimeMillis();

        if(role.getCurHp()<=0){
            TaskQueue.getQueue().remove();
        }
    }
}
