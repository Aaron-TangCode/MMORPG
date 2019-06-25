package com.game.role.bean;

import com.alibaba.fastjson.JSONObject;
import com.game.equipment.bean.EquipmentBox;
import com.game.map.bean.ConcreteMap;
import com.game.property.bean.Property;
import com.game.property.bean.PropertyType;
import com.game.property.manager.PropertyManager;
import com.game.skill.bean.ConcreteSkill;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

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
    /**
     * 角色的背包容量
     */
    private Integer backpackCapacity;

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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCurHp() {
        return curHp;
    }

    public void setCurHp(int curHp) {
        this.curHp = curHp;
    }

    public int getCurMp() {
        return curMp;
    }

    public void setCurMp(int curMp) {
        this.curMp = curMp;
    }

    public ConcreteSkill getConcreteSkill() {
        return concreteSkill;
    }

    public void setConcreteSkill(ConcreteSkill concreteSkill) {
        this.concreteSkill = concreteSkill;
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public EquipmentBox getEquipment() {
        return equipment;
    }

    public void setEquipment(EquipmentBox equipment) {
        this.equipment = equipment;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Integer getAttack() {
        return attack;
    }

    public void setAttack(Integer attack) {
        this.attack = attack;
    }

    public Integer getDefend() {
        return defend;
    }

    public void setDefend(Integer defend) {
        this.defend = defend;
    }

    public Integer getBackpackCapacity() {
        return backpackCapacity;
    }

    public void setBackpackCapacity(Integer backpackCapacity) {
        this.backpackCapacity = backpackCapacity;
    }

    private static volatile Map<PropertyType,Integer> curPropertyMap = null;
    private static volatile Map<PropertyType,Integer> totalPropertyMap = null;
    private static volatile Map<Integer, JSONObject> basicPropertyMap = null;


    public static void setCurPropertyMap(Map<PropertyType, Integer> curPropertyMap) {
        ConcreteRole.curPropertyMap = curPropertyMap;
    }

    public static void setTotalPropertyMap(Map<PropertyType, Integer> totalPropertyMap) {
        ConcreteRole.totalPropertyMap = totalPropertyMap;
    }

    public static void setBasicPropertyMap(Map<Integer, JSONObject> basicPropertyMap) {
        ConcreteRole.basicPropertyMap = basicPropertyMap;
    }

    /**
     * 存储本地的角色基础值
     * @return
     */
    public static Map<Integer, JSONObject> getBasicPropertyMap(){
        if(basicPropertyMap==null){
            synchronized (PropertyManager.class){
                if(basicPropertyMap==null){
                    basicPropertyMap = new HashMap<>();
                }
            }
        }
        return basicPropertyMap;
    }
    /**
     * 存储当前属性值的map
     * @return
     */
    public static Map<PropertyType,Integer> getCurPropertyMap(){
        if(curPropertyMap==null){
            synchronized (PropertyManager.class){
                if(curPropertyMap==null){
                    curPropertyMap = new HashMap<>();
                }
            }
        }
        return curPropertyMap;
    }

    /**
     * 存储总属性值的map
     * @return
     */
    public static Map<PropertyType,Integer> getTotalPropertyMap(){
        if(totalPropertyMap==null){
            synchronized (PropertyManager.class){
                if(totalPropertyMap==null){
                    totalPropertyMap = new HashMap<>();
                }
            }
        }
        return totalPropertyMap;
    }
}
