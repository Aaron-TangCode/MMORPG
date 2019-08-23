package com.game.event.task;


import com.game.npc.bean.ConcreteMonster;
import com.game.role.bean.ConcreteRole;

/**
 * @ClassName RecoveryMonsterTask
 * @Description 怪兽复活
 * @Author DELL
 * @Date 2019/8/22 22:13
 * @Version 1.0
 */
public class RecoveryMonsterTask implements Runnable {
    private ConcreteMonster monster;

    private ConcreteRole role;
    public RecoveryMonsterTask(ConcreteMonster monster,ConcreteRole role) {
        this.monster = monster;
        this.role = role;
    }

    @Override
    public void run() {
        //怪兽复活
        monster.setHp(500);
        //
        role.getQueue().remove();
    }
}
