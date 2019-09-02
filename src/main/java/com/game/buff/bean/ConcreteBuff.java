package com.game.buff.bean;

import com.game.role.bean.ConcreteRole;

/**
 * @ClassName ConcreteBuff
 * @Description buff实体类
 * @Author DELL
 * @Date 2019/6/20 16:31
 * @Version 1.0
 */
public class ConcreteBuff {
    /**
     * Buff的id
     */
    private int id;
    /**
     * buff名字
     */
    private String name;
    /**
     * 持续时间
     */
    private long keepTime;
    /**
     * 时间段
     */
    private long period;
    /**
     * Buff效果
     */
    private int effect;
    /**
     * buff的hp
     */
    private int hp;
    /**
     * buff的mp
     */
    private int mp;
    /**
     * buff的defend
     */
    private int defend;
    /**
     * buff的attack
     */
    private int attack;
    /**
     * 角色
     */
    private ConcreteRole role;

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
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

    public long getKeepTime() {
        return keepTime;
    }

    public void setKeepTime(long keepTime) {
        this.keepTime = keepTime;
    }

    public long getPeriod() {
        return period;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setPeriod(long period) {
        this.period = period;
    }

    public int getEffect() {
        return effect;
    }

    public void setEffect(int effect) {
        this.effect = effect;
    }

    public int getDefend() {
        return defend;
    }

    public void setDefend(int defend) {
        this.defend = defend;
    }

    public ConcreteRole getRole() {
        return role;
    }

    public void setRole(ConcreteRole role) {
        this.role = role;
    }

    public ConcreteBuff(int id, String name, long keepTime, long period, int effect, int hp, int mp, int defend, int attack, ConcreteRole role) {
        this.id = id;
        this.name = name;
        this.keepTime = keepTime;
        this.period = period;
        this.effect = effect;
        this.hp = hp;
        this.mp = mp;
        this.defend = defend;
        this.attack = attack;
        this.role = role;
    }

    public ConcreteBuff() {
    }
}
