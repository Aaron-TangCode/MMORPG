package com.game.gang.bean;

/**
 * @ClassName GangEntity
 * @Description 工会实体类
 * @Author DELL
 * @Date 2019/7/16 10:02
 * @Version 1.0
 */
public class GangEntity {
    private Integer id;
    private String name;
    private Integer level;
    private Integer funds;

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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getFunds() {
        return funds;
    }

    public void setFunds(Integer funds) {
        this.funds = funds;
    }
}
