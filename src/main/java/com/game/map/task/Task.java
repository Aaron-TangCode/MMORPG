package com.game.map.task;

import com.game.map.threadpool.TaskQueue;
import com.game.npc.bean.ConcreteMonster;
import com.game.role.bean.ConcreteRole;
import io.netty.channel.Channel;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName DispatcherTask
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

    /**
     * boss发动攻击
     * @param boss
     * @param role
     */
    private void bossAttack(ConcreteMonster boss, ConcreteRole role) {
        //boss的攻击力

        Integer attack = boss.getAttack();
        //角色的当前血量
        int curHp = role.getCurHp();
        //角色被攻击，减少响应血量
        role.setCurHp(curHp-attack);

        Channel channel = role.getChannel();
        //输出
        channel.writeAndFlush(boss.getName()+"攻击"+role.getName()+"\t角色的血量："+role.getCurHp());

        if(role.getCurHp()<=0){
            TaskQueue.getQueue().remove();
            channel.writeAndFlush("角色死亡，执行副本任务失败");
        }
    }
}
