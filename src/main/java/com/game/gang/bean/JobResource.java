package com.game.gang.bean;

/**
 * @ClassName JobResource
 * @Description excelBean
 * @Author DELL
 * @Date 2019/7/16 14:20
 * @Version 1.0
 */
public class JobResource {
    private Integer id;
    private String job;
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
