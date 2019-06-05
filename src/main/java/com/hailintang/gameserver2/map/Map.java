package com.hailintang.gameserver2.map;

import com.hailintang.gameserver2.role.ParentRole;

import java.util.List;

/**
 * @ClassName Map
 * @Description 地图接口
 * @Author DELL
 * @Date 2019/5/2410:17
 * @Version 1.0
 */
public interface Map {
    /**
     * 地图的唯一id
     * @return
     */
    public int getId();

    /**
     * 地图的名字
     * @return
     */
    public String getName();

    /**
     * 地图的显示
     */
    public void display();

    public List<ParentRole> getList();
}
