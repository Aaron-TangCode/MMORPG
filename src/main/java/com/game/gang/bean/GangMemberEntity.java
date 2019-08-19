package com.game.gang.bean;

import com.game.role.bean.ConcreteRole;

/**
 * @ClassName GangMemberEntity
 * @Description 工会成员
 * @Author DELL
 * @Date 2019/7/16 11:58
 * @Version 1.0
 */
public class GangMemberEntity {
    /**
     * 角色
     */
    private ConcreteRole role;
    /**
     * 工会
     */
    private GangEntity gang;
    /**
     * 职位
     */
    private String job;

    public ConcreteRole getRole() {
        return role;
    }

    public void setRole(ConcreteRole role) {
        this.role = role;
    }

    public GangEntity getGang() {
        return gang;
    }

    public void setGang(GangEntity gang) {
        this.gang = gang;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
