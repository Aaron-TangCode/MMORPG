package com.hailintang.gameserver2.State;

import com.hailintang.gameserver2.role.ParentRole;

/**
 * @ClassName AliveState
 * @Description TODO
 * @Author DELL
 * @Date 2019/5/2412:15
 * @Version 1.0
 */
public class AliveState implements State {
    private String name;
    public AliveState(String name){
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
