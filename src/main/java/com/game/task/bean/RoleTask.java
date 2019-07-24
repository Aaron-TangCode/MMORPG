package com.game.task.bean;

/**
 * @ClassName RoleTask
 * @Description 角色任务
 * @Author DELL
 * @Date 2019/7/22 18:11
 * @Version 1.0
 */
public class RoleTask {
    private Integer roleId;
    private String receivedTask;
    private String finishedTask;

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
}
