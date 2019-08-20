package com.game.task.bean;

/**
 * @ClassName RoleTask
 * @Description 角色任务
 * @Author DELL
 * @Date 2019/7/22 18:11
 * @Version 1.0
 */
public class RoleTask {
    /**
     * 角色id
     */
    private Integer roleId;
    /**
     * 已接受任务
     */
    private String receivedTask;
    /**
     * 已完成任务
     */
    private String finishedTask;
    /**
     * 完成任务条件
     */
    private Integer count;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getReceivedTask() {
        return receivedTask;
    }

    public void setReceivedTask(String receivedTask) {
        this.receivedTask = receivedTask;
    }

    public String getFinishedTask() {
        return finishedTask;
    }

    public void setFinishedTask(String finishedTask) {
        this.finishedTask = finishedTask;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;

    }
}
