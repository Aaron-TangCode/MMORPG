package com.hailintang.gameserver2.role;

import com.hailintang.gameserver2.State.State;
import com.hailintang.gameserver2.map.Map;

/**
 * @ClassName ParentRole
 * @Description TODO
 * @Author DELL
 * @Date 2019/5/2410:37
 * @Version 1.0
 */
public interface ParentRole {
    public State getState();
    public void setState(State state);
    public void moveTo(Map src, Map dest);
    public boolean isExistCurMap(Map map);
    public void getLocation();
}
