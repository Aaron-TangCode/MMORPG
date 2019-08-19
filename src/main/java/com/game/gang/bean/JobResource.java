package com.game.gang.bean;

/**
 * @ClassName JobResource
 * @Description 职位
 * @Author DELL
 * @Date 2019/7/16 14:20
 * @Version 1.0
 */
public class JobResource {
    /**
     * 职位Id
     */
    private Integer id;
    /**
     * 职位
     */
    private String job;
    /**
     * 职位权限
     */
    private String permission;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
