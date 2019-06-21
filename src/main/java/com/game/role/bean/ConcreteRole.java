package com.game.role.bean;

import com.game.equipment.bean.EquipmentBox;
import com.game.map.bean.ConcreteMap;
import com.game.property.bean.Property;
import com.game.property.manager.PropertyManager;
import com.game.skill.bean.ConcreteSkill;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName ConcreteRole
 * @Description 游戏角色实体类
 * @Author DELL
 * @Date 2019/5/3015:14
 * @Version 1.0
 */
@Component
public class ConcreteRole {
    /**
     * 角色id
     */
    private int id;
    /**
     * 角色name
     */
    private String name;
    /**
     * 角色所在地图map
     */
    private ConcreteMap concreteMap;
    /**
     * 角色等级
     */
    private int level;
    /**
     * 角色总血量
     */
    private int curHp;
    /**
     * 角色总魔法值
     */
    private int curMp;

    private ConcreteSkill concreteSkill;

    private ChannelHandlerContext ctx;

    private EquipmentBox equipment;
    @Autowired
    private Property property;

    private Integer attack;

    private Integer defend;

    public Integer getAttack() {
        return attack;
    }

    public void setAttack() {
        this.attack = PropertyManager.getMap().get(getLevel()).getAttack();
    }

    public Integer getDefend() {
        return defend;
    }

    public void setDefend() {
        this.defend = PropertyManager.getMap().get(getLevel()).getDefend();
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public EquipmentBox getEquipment() {
        return equipment;
    }

    public void setEquipment(EquipmentBox equipment) {
        this.equipment = equipment;
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public int getCurMp() {
        return curMp;
    }

    public void setMp() {
        this.curMp = PropertyManager.getMap().get(getLevel()).getMp();
    }

    public ConcreteSkill getConcreteSkill() {
        return concreteSkill;
    }

    public void setConcreteSkill(ConcreteSkill concreteSkill) {
        this.concreteSkill = concreteSkill;
    }

    public int getCurHp() {
        return curHp;
    }

    public void setHp() {
        this.curHp = PropertyManager.getMap().get(getLevel()).getHp();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ConcreteMap getConcreteMap() {
        return concreteMap;
    }

    public void setConcreteMap(ConcreteMap concreteMap) {
        this.concreteMap = concreteMap;
    }
}
