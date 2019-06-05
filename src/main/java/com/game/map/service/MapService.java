package com.game.map.service;

import com.game.map.bean.ConcreteMap;
import com.game.map.repository.MapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName MapService
 * @Description 地图service
 * @Author DELL
 * @Date 2019/5/2921:05
 * @Version 1.0
 */
@Service("MapService")
public class MapService {
    @Autowired
    private MapRepository mapRepository;

    /**
     * 根据id获取地图实体类
     * @param id
     * @return
     */
    public ConcreteMap getMap(int id){
        return mapRepository.getMap(id);
    }

    /**
     * 根据地图名字获取地图id
     * @param name
     * @return
     */
    public int getMapIdByMapName(String name) {
        return mapRepository.getId(name);
    }
}
