package com.game.notice;

import com.game.npc.bean.ConcreteMonster;

/**
 * @ClassName NoticeBean
 * @Description 通知
 * @Author DELL
 * @Date 2019/6/14 16:37
 * @Version 1.0
 */
public class NoticeBean {
    /**
     * 通知类型
     */
    private String type;
    /**
     * 角色名字
     */
    private String roleName;
    /**
     * 怪兽
     */
    private ConcreteMonster monster;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public ConcreteMonster getMonster() {
        return monster;
    }

    public void setMonster(ConcreteMonster monster) {
        this.monster = monster;
    }
}
