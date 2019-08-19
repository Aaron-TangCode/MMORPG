package com.game.npc.controller;

import com.game.dispatcher.RequestAnnotation;
import com.game.utils.MapUtils;
import org.springframework.stereotype.Component;
/**
 * @ClassName MonsterController
 * @Description 怪兽控制器
 * @Author DELL
 * @Date 2019/6/14 14:36
 * @Version 1.0
 */
@Component
@RequestAnnotation("/monster")
public class MonsterController {
    /**
     * 获取怪兽状态
     * @return 信息
     */
    @RequestAnnotation("/getState")
    public String getState(Integer monsterId){
        //获取怪兽名
        String name = MapUtils.getMonsterMap().get(monsterId).getName();
        //获取怪兽血量
        Integer hp = MapUtils.getMonsterMap().get(monsterId).getHp();
        return name+"的状态是："+(hp>0?"生存":"死亡");
    }
}
