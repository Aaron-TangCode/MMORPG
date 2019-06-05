package com.game.role.bean;

import com.game.map.bean.ConcreteMap;

/**
 * @ClassName ConcreteRole
 * @Description 游戏角色实体类
 * @Author DELL
 * @Date 2019/5/3015:14
 * @Version 1.0
 */
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
     * 角色血量
     */
    private int hp = 100;

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
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

    /**
     * 返回角色状态
     * @return
     */
    public String getState(){
       return getHp()>0?"生存":"死亡";
    }
}
