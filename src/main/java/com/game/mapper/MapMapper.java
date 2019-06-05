package com.game.mapper;

import com.game.map.bean.ConcreteMap;
import com.game.map.Map_Mapping;

import java.util.List;

/**
 * @ClassName MapMapper
 * @Description TODO
 * @Author DELL
 * @Date 2019/5/2921:03
 * @Version 1.0
 */
public interface MapMapper {
    public ConcreteMap getMapById(int id);
    public ConcreteMap getMapIdByMapName(String name);

    public Integer getIdByName(String name);
    public List<Map_Mapping> getMap_Mapping();
}
