package com.game.map.repository;

import com.game.map.bean.ConcreteMap;
import com.game.map.Map_Mapping;
import com.game.mapper.MapMapper;
import com.game.utils.SqlUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName MapRepository
 * @Description TODO
 * @Author DELL
 * @Date 2019/5/2920:42
 * @Version 1.0
 */
@Repository("MapRepository")
public class MapRepository {
    public ConcreteMap getMap(int id){
        SqlSession session = SqlUtils.getSession();
        MapMapper mapper = session.getMapper(MapMapper.class);
        ConcreteMap map = mapper.getMapById(id);
        return map;
    }

    public Integer getMapIdByMapName(String name) {
        SqlSession session = SqlUtils.getSession();
        MapMapper mapper = session.getMapper(MapMapper.class);
        ConcreteMap map = mapper.getMapIdByMapName(name);
        return map.getId();
    }

    public Integer getId(String name) {
        SqlSession session = SqlUtils.getSession();
        MapMapper mapper = session.getMapper(MapMapper.class);
        Integer id = mapper.getIdByName(name);
        return id;
    }

    public List<Map_Mapping> getMap_Maping(){
        SqlSession session = SqlUtils.getSession();
        MapMapper mapper = session.getMapper(MapMapper.class);
        List<Map_Mapping> list = mapper.getMap_Mapping();
        return list;
    }
}
