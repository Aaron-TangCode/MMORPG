package com.game.role.bean;

import com.game.map.bean.ConcreteMap;
import com.game.skill.bean.ConcreteSkill;
import io.netty.channel.ChannelHandlerContext;
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
     * 角色血量
     */
    private int hp = 100;
    /**
     * 角色魔法值
     */
    private int mp = 100;

    private ConcreteSkill concreteSkill;

    private ChannelHandlerContext ctx;

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public ConcreteSkill getConcreteSkill() {
        return concreteSkill;
    }

    public void setConcreteSkill(ConcreteSkill concreteSkill) {
        this.concreteSkill = concreteSkill;
    }

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

}
