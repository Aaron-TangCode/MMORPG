package com.game.occupation.bean;

import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName Occupation
 * @Description 职业
 * @Author DELL
 * @Date 2019/7/3 14:52
 * @Version 1.0
 */
public class Occupation {
    /**
     * id
     */
    private Integer id;
    /**
     * 职业名字
     */
    private String name;
    /**
     * 仇恨值
     */
    private Integer attract;
    /**
     * 描述
     */
    private String description;
    /**
     * 属性
     */
    private JSONObject property;

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

    public Integer getAttract() {
        return attract;
    }

    public void setAttract(Integer attract) {
        this.attract = attract;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JSONObject getProperty() {
        return property;
    }

    public void setProperty(JSONObject property) {
        this.property = property;
    }
}
