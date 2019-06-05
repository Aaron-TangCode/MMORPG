package com.hailintang.gameserver2.State;

import com.hailintang.gameserver2.role.ParentRole;

/**
 * @ClassName DeadState
 * @Description TODO
 * @Author DELL
 * @Date 2019/5/2412:16
 * @Version 1.0
 */
public class DeadState implements State {
    private String name;
    public DeadState(String name){
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
