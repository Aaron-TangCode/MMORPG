package com.game.duplicate.bean;

import com.game.role.bean.ConcreteRole;

import java.util.List;

/**
 * @ClassName Team
 * @Description 队伍
 * @Author DELL
 * @Date 2019/8/23 12:15
 * @Version 1.0
 */
public class Team {
    private Integer id;
    private String name;
    private List<ConcreteRole> roleList;

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

    public List<ConcreteRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<ConcreteRole> roleList) {
        this.roleList = roleList;
    }
}
