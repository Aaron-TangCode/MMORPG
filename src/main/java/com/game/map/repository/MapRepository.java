package com.game.map.repository;

import com.game.map.bean.ConcreteMap;
import com.game.mapper.MapMapper;
import com.game.utils.SqlUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;


/**
 * @ClassName MapRepository
 * @Description 地图repository
 * @Author DELL
 * @Date 2019/5/2920:42
 * @Version 1.0
 */
@Repository("MapRepository")
public class MapRepository {
    /**
     * 根据地图id获取地图实体类
     * @param id
     * @return
     */
    public ConcreteMap getMap(int id){
        SqlSession session = SqlUtils.getSession();
        MapMapper mapper = session.getMapper(MapMapper.class);
        ConcreteMap map = mapper.getMapById(id);
        return map;
    }

    /**
     * 通过地图名字获取地图id
     * @param name
     * @return
     */
    public Integer getId(String name) {
        SqlSession session = SqlUtils.getSession();
        MapMapper mapper = session.getMapper(MapMapper.class);
        Integer id = mapper.getIdByName(name);
        return id;
    }
}
