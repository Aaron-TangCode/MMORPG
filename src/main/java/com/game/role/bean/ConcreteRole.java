package com.game.role.bean;

import com.alibaba.fastjson.JSONObject;
import com.game.equipment.bean.EquipmentBox;
import com.game.map.bean.ConcreteMap;
import com.game.occupation.bean.Occupation;
import com.game.property.bean.Property;
import com.game.property.bean.PropertyType;
import com.game.property.manager.PropertyManager;
import com.game.skill.bean.ConcreteSkill;
import com.game.task.bean.ConcreteTask;
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
     * 角色等级
     */
    private int level;
    /**
     * 角色当前血量
     */
    private int curHp;
    /**
     * 角色当前魔法值
     */
    private int curMp;

    /**
     * 角色总血量
     */
    private int totalHp;
    /**
     * 角色总魔法值
     */
    private int totalMp;
    /**
     * 攻击力
     */
    private Integer attack;
    /**
     * 防御力
     */
    private Integer defend;
    /**
     * 角色的背包容量
     */
    private Integer backpackCapacity;
    /**
     * 玩家拥有的金币
     */
    private Integer money;


    /**
     * 角色所在地图map
     */
    private ConcreteMap concreteMap;
    /**
     * 技能
     */
    private ConcreteSkill concreteSkill;
    /**
     * ctx
     */
    private ChannelHandlerContext ctx;
    /**
     * 装备栏
     */
    private EquipmentBox equipmentBox;
    /**
     * 属性
     */
    @Autowired
    private Property property;
    /**
     * 角色职业
     */
    private Occupation occupation;
    /**
     * 可接受的任务
     */
    private Map<Integer, ConcreteTask> receivableTaskMap;
    /**
     * 已接受的任务
     */
    private Map<Integer,ConcreteTask> receivedTaskMap;
    /**
     * 已完成的任务
     */
    private Map<Integer,ConcreteTask> finishedTaskMap;





    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    private Map<PropertyType,Integer> totalMap = new HashMap<>();

    private Map<PropertyType,Integer> curMap = new HashMap<>();

    public int getTotalHp() {
        return totalHp;
    }

    public void setTotalHp(int totalHp) {
        this.totalHp = totalHp;
    }

    public int getTotalMp() {
        return totalMp;
    }

    public void setTotalMp(int totalMp) {
        this.totalMp = totalMp;
    }

    public void setTotalMap(Map<PropertyType, Integer> totalMap) {
        this.totalMap = totalMap;
    }

    public void setCurMap(Map<PropertyType, Integer> curMap) {
        this.curMap = curMap;
    }


    public Map<PropertyType, Integer> getTotalMap() {
        return totalMap;
    }
    public Map<PropertyType, Integer> getCurMap() {
        return curMap;
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

    public EquipmentBox getEquipmentBox() {
        return equipmentBox;
    }

    public void setEquipmentBox(EquipmentBox equipmentBox) {
        this.equipmentBox = equipmentBox;
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

    private static volatile Map<Integer, JSONObject> basicPropertyMap = null;

    public static void setBasicPropertyMap(Map<Integer, JSONObject> basicPropertyMap) {
        ConcreteRole.basicPropertyMap = basicPropertyMap;
    }

    /**
     * 存储本地的角色基础值(存excel表的数据)
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

    public Map<Integer, ConcreteTask> getReceivableTaskMap() {
        return receivableTaskMap;
    }

    public void setReceivableTaskMap(Map<Integer, ConcreteTask> receivableTaskMap) {
        this.receivableTaskMap = receivableTaskMap;
    }

    public Map<Integer, ConcreteTask> getReceivedTaskMap() {
        return receivedTaskMap;
    }

    public void setReceivedTaskMap(Map<Integer, ConcreteTask> receivedTaskMap) {
        this.receivedTaskMap = receivedTaskMap;
    }

    public Map<Integer, ConcreteTask> getFinishedTaskMap() {
        return finishedTaskMap;
    }

    public void setFinishedTaskMap(Map<Integer, ConcreteTask> finishedTaskMap) {
        this.finishedTaskMap = finishedTaskMap;
    }

    public Occupation getOccupation() {
        return occupation;
    }

    public void setOccupation(Occupation occupation) {
        this.occupation = occupation;
    }
}
