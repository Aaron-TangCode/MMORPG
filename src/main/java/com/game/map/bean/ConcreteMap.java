package com.game.map.bean;

import com.game.npc.bean.ConcreteMonster;
import com.game.role.bean.ConcreteRole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ConcreteMap
 * @Description 地图实体类
 * @Author DELL
 * @Date 2019/5/3015:09
 * @Version 1.0
 */
public class ConcreteMap {
    /**
     * 唯一id
     */
    private int id;
    /**
     * 地图名
     */
    private String name;
    /**
     * 怪兽的引用
     */
    private Map<Integer, ConcreteMonster> monsterMap = new HashMap<>();
    /**
     * 怪兽引用
     */
    private ConcreteMonster monster;

    public ConcreteMonster getMonster() {
        return monster;
    }

    public void setMonster(ConcreteMonster monster) {
        this.monster = monster;
    }

    private List<ConcreteRole> roleList = new ArrayList<>();

    public void setMonsterMap(Map<Integer, ConcreteMonster> monsterMap) {
        this.monsterMap = monsterMap;
    }

    public void setRoleList(List<ConcreteRole> roleList) {
        this.roleList = roleList;
    }

    public List<ConcreteRole> getRoleList() {
        return roleList;
    }

    public Map<Integer, ConcreteMonster> getMonsterMap() {
        return monsterMap;
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

    @Override
    public String toString() {
        return "ConcreteMap{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public ConcreteMap(ConcreteMap map){
        this.id = map.getId();
        this.name = map.getName();
    }

    public ConcreteMap(){}

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
