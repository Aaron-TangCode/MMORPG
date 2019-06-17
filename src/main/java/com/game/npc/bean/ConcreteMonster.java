package com.game.npc.bean;

/**
 * @ClassName ConcreteMonster
 * @Description 怪物实体类
 * @Author DELL
 * @Date 2019/6/11 17:41
 * @Version 1.0
 */
public class ConcreteMonster {
    /**
     * 唯一id
     */
    private Integer id;
    /**
     * 名字
     */
    private String name;
    /**
     * 怪兽血量
     */
    private Integer hp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHp() {
        return hp;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }

    /**
     * monster的状态
     * @return
     */
    public String getState(){
        return getHp()>0?"生存":"死亡";
    }
}
