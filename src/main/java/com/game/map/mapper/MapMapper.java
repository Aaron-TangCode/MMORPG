package com.game.map.mapper;

import com.game.map.bean.ConcreteMap;

/**
 * @ClassName MapMapper
 * @Description 地图mapper
 * @Author DELL
 * @Date 2019/5/2921:03
 * @Version 1.0
 */
public interface MapMapper {
    /**
     * 根据地图id获取
     * @param id id
     * @return map
     */
    public ConcreteMap getMapById(int id);

    /**
     * 根据地图名字获取地图id
     * @param name 地图名
     * @return the id of map
     */
    public Integer getIdByName(String name);

    /**
     * 根据roleId获取地图
     * @param roleId the id of role
     * @return map
     */
    public ConcreteMap getMapByRoleId(int roleId);

}
