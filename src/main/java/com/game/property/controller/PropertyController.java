package com.game.property.controller;

import com.game.property.bean.Property;
import com.game.property.manager.PropertyManager;
import com.game.role.bean.ConcreteRole;
import com.game.utils.MapUtils;
import org.springframework.stereotype.Component;

/**
 * @ClassName PropertyController
 * @Description TODO
 * @Author DELL
 * @Date 2019/6/21 12:07
 * @Version 1.0
 */
@Component
public class PropertyController {

    public Property getProperty(String roleName){
        ConcreteRole role = MapUtils.getMapRolename_Role().get(roleName);
        Property property = PropertyManager.getMap().get(role.getLevel());
        return property;
    }

    public int getHp(String roleName){
        Property property = getProperty(roleName);
        return property.getHp();
    }

    public int getMp(String roleName){
        Property property = getProperty(roleName);
        return property.getMp();
    }

    public int getAttack(String roleName){
        Property property = getProperty(roleName);
        return property.getAttack();
    }

    public int getDefend(String roleName){
        Property property = getProperty(roleName);
        return property.getDefend();
    }
}
