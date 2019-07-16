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
    private ConcreteRole role;
    private GangEntity gang;
    private Job job;

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

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}
