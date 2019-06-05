package com.hailintang.gameserver2.State;

import com.hailintang.gameserver2.role.ParentRole;

/**
 * @ClassName ForbidState
 * @Description 禁用状态
 * @Author DELL
 * @Date 2019/5/2710:58
 * @Version 1.0
 */
public class ForbidState implements State {
    private String name;

    public ForbidState(String name) {
        this.name = name;
    }

    @Override
    public void doAction(ParentRole role) {
        role.setState(this);
    }

    @Override
    public String getStateName() {
        return name;
    }
}
