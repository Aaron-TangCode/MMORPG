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
    private int id;
    private String name;
    private long keeptime;
    private long period;
    private int effect;
    private int hp;
    private int mp;
    private int defend;
    private int attack;
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

    public long getKeeptime() {
        return keeptime;
    }

    public void setKeeptime(long keeptime) {
        this.keeptime = keeptime;
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

    public ConcreteBuff(int id, String name, long keeptime, long period, int effect, int hp, int mp, int defend, int attack, ConcreteRole role) {
        this.id = id;
        this.name = name;
        this.keeptime = keeptime;
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
