package com.game.gang.bean;

public enum JobPermission {
    /**
     * 工会职业权限
     */
    HANDLE_APPLICATION("处理申请"),
    /**
     * 踢人
     */
    KICK("踢人"),
    /**
     * 退出
     */
    EXIT("退出"),
    /**
     * 解散
     */
    DISMISS("解散");

    /**
     * 功能名字
     */
    String name;
    JobPermission(String name){
        this.name = name;
    }
}
