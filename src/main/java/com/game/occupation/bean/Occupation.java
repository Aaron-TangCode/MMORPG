package com.game.occupation.bean;

import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName Occupation
 * @Description TODO
 * @Author DELL
 * @Date 2019/7/3 14:52
 * @Version 1.0
 */
public class Occupation {
    private Integer id;
    private String name;
    private Integer attract;
    private String description;
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
