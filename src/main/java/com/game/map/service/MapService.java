package com.game.map.service;

import com.game.map.bean.ConcreteMap;
import com.game.map.repository.MapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName MapService
 * @Description TODO
 * @Author DELL
 * @Date 2019/5/2921:05
 * @Version 1.0
 */
@Service("MapService")
public class MapService {
    @Autowired
    private MapRepository mapRepository;

    public ConcreteMap getMap(int id){
        ConcreteMap map = mapRepository.getMap(id);
        return map;
    }

    public int getMapIdByMapName(String name) {
        return mapRepository.getId(name);
    }
}
