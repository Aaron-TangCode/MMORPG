package com.game.role.manager;

import com.game.property.bean.PropertyType;
import com.game.role.bean.ConcreteRole;
import com.game.utils.CacheUtils;

import java.util.Map;

/**
 * @ClassName InjectRoleProperty
 * @Description 属性模块的数据注入角色模块
 * @Author DELL
 * @Date 2019/6/27 12:49
 * @Version 1.0
 */
public class InjectRoleProperty {
    /**
     * 把属性模块的数据注入角色模块
     * @param role
     */
    public static void injectRoleProperty(ConcreteRole role){
        //获取属性模块数据
        Map<PropertyType, Integer> curMap = role.getCurMap();
        Map<PropertyType, Integer> totalMap = role.getTotalMap();
        //注入属性
        Integer curHp = curMap.get(PropertyType.HP);
        Integer curMp = curMap.get(PropertyType.MP);
        Integer attack = totalMap.get(PropertyType.ATTACK);
        Integer defend = totalMap.get(PropertyType.DEFEND);
        Integer totalHp = totalMap.get(PropertyType.HP);
        Integer totalMp = totalMap.get(PropertyType.MP);
        //更新数据
        role.setCurHp(curHp);
        role.setCurMp(curMp);
        role.setTotalHp(totalHp);
        role.setTotalMp(totalMp);
        role.setAttack(attack);
        role.setDefend(defend);
        //写入缓存
        CacheUtils.addRole(role.getName(),role);

    }
}
