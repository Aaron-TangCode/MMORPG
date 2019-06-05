package com.game.mapper;

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
     * @param id
     * @return
     */
    public ConcreteMap getMapById(int id);

    /**
     * 根据地图名字获取地图id
     * @param name
     * @return
     */
    public Integer getIdByName(String name);

}
