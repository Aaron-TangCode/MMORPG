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
        if(role.getCurHp()<role.getCurHp()){
            int tempHp = role.getCurHp()+buff.getHp();
            int newHp = tempHp>100?100:tempHp;
//            Property property = PropertyManager.getMap().get(role.getLevel());
//            property.setHp(newHp);
//
//            role.setHp();
            System.out.println("自动恢复血量");
        }
        if(role.getCurMp()<100){
           int tempMp = role.getCurMp()+buff.getMp();
           int newMp = tempMp>100?100:tempMp;
//            Property property = PropertyManager.getMap().get(role.getLevel());
//            property.setHp(newMp);
//           role.setMp();
            System.out.println("自动恢复魔法值");
        }

    }
}
