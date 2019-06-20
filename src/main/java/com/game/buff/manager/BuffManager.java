package com.game.buff.manager;

import com.game.buff.bean.ConcreteBuff;
import com.game.role.bean.ConcreteRole;

/**
 * @ClassName BuffManager
 * @Description TODO
 * @Author DELL
 * @Date 2019/6/20 17:42
 * @Version 1.0
 */
public class BuffManager {
    public static void startBuff(ConcreteBuff buff,ConcreteRole role){
        if(role.getHp()<100){
            int tempHp = role.getHp()+buff.getHp();
            int newHp = tempHp>100?100:tempHp;
            role.setHp(newHp);
            System.out.println("自动恢复血量");
        }
        if(role.getMp()<100){
           int tempMp = role.getMp()+buff.getMp();
           int newHp = tempMp>100?100:tempMp;
           role.setMp(newHp);
            System.out.println("自动恢复魔法值");
        }

    }
}
