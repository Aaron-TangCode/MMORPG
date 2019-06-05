package com.hailintang.gameserver2.State;

import com.hailintang.gameserver2.role.ParentRole;

/**
 * @ClassName State
 * @Description 状态信息
 * @Author DELL
 * @Date 2019/5/2412:14
 * @Version 1.0
 */
public interface State {
    public void doAction(ParentRole role);
    public String getStateName();
}
