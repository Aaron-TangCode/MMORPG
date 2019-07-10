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
    /**
     * 击败boss的最大时间
     */
    private Integer time;
    /**
     * boss每次自动攻击的相隔时间
     */
    private Long attackTime;
    /**
     * boss的攻击力
     */
    private Integer attack;
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

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Long getAttackTime() {
        return attackTime;
    }

    public void setAttackTime(Long attackTime) {
        this.attackTime = attackTime;
    }

    public Integer getAttack() {
        return attack;
    }

    public void setAttack(Integer attack) {
        this.attack = attack;
    }

    /**
     * monster的状态
     * @return
     */
    public String getState(){
        return getHp()>0?"生存":"死亡";
    }
}
