package com.hailintang.gameserver2.role;

import com.hailintang.gameserver2.State.State;
import com.hailintang.gameserver2.map.CentralMap;
import com.hailintang.gameserver2.map.Map;
import com.hailintang.gameserver2.map.MapInfo;

/**
 * @ClassName Role_01
 * @Description TODO
 * @Author DELL
 * @Date 2019/5/2410:34
 * @Version 1.0
 */
public class Role_01 implements ParentRole {
    private Map map;
    private String name;
    private State state;

    public Role_01(Map map,String name){
        this.map = map;
        this.name = name;
        this.map.getList().add(this);
        MapInfo.getMap().put(map.getId(),map.getList());
    }

    @Override
    public State getState() {
        return state;
    }

    @Override
    public void  setState(State state) {
        this.state = state;
    }

    @Override
    public void moveTo(Map src, Map dest) {
        map = CentralMap.moveTo(src, dest, this);
    }

    /**
     * 判断人物是否在当前地图
     * @param map
     * @return
     */
    @Override
    public boolean isExistCurMap(Map map) {
       return this.map.getId()==map.getId();
    }
    @Override
    public void getLocation(){
        map.display();
    }
    @Override
    public String toString() {
        return "name=" + name ;
    }
}
