package com.game.skill.bean;

/**
 * @ClassName ConcreteSkill
 * @Description 技能实体类
 * @Author DELL
 * @Date 2019/6/13 11:07
 * @Version 1.0
 */
public class ConcreteSkill {
    /**
     * 唯一id
     */
    private String id;
    /**
     * 技能名称
     */
    private String name;
    /**
     * 技能的消耗的魔法值
     */
    private Integer mp;
    /**
     * 技能的冷却时间
     */
    private Integer cd;
    /**
     * 技能的伤害值
     */
    private Integer hurt;
    /**
     * 技能描述
     */
    private String description;
    /**
     * 技能等级
     */
    private Integer level;
    /**
     * 技能升级条件
     */
    private String condition;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMp() {
        return mp;
    }

    public void setMp(Integer mp) {
        this.mp = mp;
    }

    public Integer getCd() {
        return cd;
    }

    public void setCd(Integer cd) {
        this.cd = cd;
    }

    public Integer getHurt() {
        return hurt;
    }

    public void setHurt(Integer hurt) {
        this.hurt = hurt;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
