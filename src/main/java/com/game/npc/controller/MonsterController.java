package com.game.npc.controller;

import com.game.dispatcher.RequestAnnotation;
import com.game.utils.MapUtils;
import org.springframework.stereotype.Component;
/**
 * @ClassName MonsterController
 * @Description TODO
 * @Author DELL
 * @Date 2019/6/14 14:36
 * @Version 1.0
 */
@Component
@RequestAnnotation("/monster")
public class MonsterController {
    /**
     * 获取怪兽状态
     * @return
     */
    @RequestAnnotation("/getState")
    public String getState(Integer monsterId){
        String name = MapUtils.getMonsterMap().get(monsterId).getName();
        Integer hp = MapUtils.getMonsterMap().get(monsterId).getHp();
        return name+"的状态是："+(hp>0?"生存":"死亡");
    }
}
