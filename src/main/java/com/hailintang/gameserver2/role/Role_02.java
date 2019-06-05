package com.hailintang.gameserver2.role;

import com.hailintang.gameserver2.State.State;
import com.hailintang.gameserver2.map.Map;
import com.hailintang.gameserver2.map.MapInfo;
import com.hailintang.gameserver2.map.CentralMap;

/**
 * @ClassName Role_02
 * @Description TODO
 * @Author DELL
 * @Date 2019/5/2711:00
 * @Version 1.0
 */
public class Role_02 implements ParentRole {
    private State state;
    private Map map;
    private String name;

    public Role_02(Map map,String name){
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
    public void setState(State state) {
        this.state = state;
    }

    @Override
    public void moveTo(Map src, Map dest) {
//        if(CentralMap.isReach(src,dest)){
//            map = dest;
//            //添加role到场景中
//            CentralMap.handleRole(src,dest,this);
//        }else{
//            map = src;
//        }
        map = CentralMap.moveTo(src,dest,this);
    }

    @Override
    public boolean isExistCurMap(Map map) {
        return this.map.getId()==map.getId();
    }

    @Override
    public void getLocation() {
        map.display();
    }

    @Override
    public String toString() {
        return "name="+name;
    }
}
