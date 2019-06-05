package com.game.map.controller;

import com.game.dispatcher.RequestAnnotation;
import com.game.map.bean.ConcreteMap;
import com.game.role.bean.ConcreteRole;
import com.game.map.service.MapService;
import com.game.role.service.RoleService;
import com.game.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName MapController
 * @Description TODO
 * @Author DELL
 * @Date 2019/5/2921:07
 * @Version 1.0
 */
@RequestAnnotation("/map")
@Component
public class MapController {

    @Autowired
    private MapService mapService;

    @Autowired
    private RoleService roleService;

    @RequestAnnotation("/getMap")
    public ConcreteMap getMap(int id){
        return mapService.getMap(id);
    }

    @RequestAnnotation("/moveTo")
    public String moveTo(String username,String dest) {
        //获取角色信息
        System.out.println(MapUtils.getMapRole().size());
        ConcreteRole role = MapUtils.getMapRole().get(username);
        //获取角色的原地点
        String src = role.getConcreteMap().getName();
        //获取源地点和目的地点的id
        int src_id = mapService.getMapIdByMapName(src);
        int dest_id = mapService.getMapIdByMapName(dest);
        //判断是否可达
        boolean isAccess = MapUtils.isReach(src_id,dest_id);
        if(isAccess){
            //从src移动到dest,更新数据库
            roleService.updateMap(role.getName(),dest_id);
            role.getConcreteMap().setName(dest);
            //更新本地缓存
            MapUtils.getMapRole().put(username,role);
            return role.getName()+"从"+src+"移动到"+dest;
        }else{
            return "不能从"+src+"直接移动到"+dest;
        }
    }

    /**
     * 打印当前地图的所有上线的角色信息
     * @param mname 地图名
     * @return
     */
    @RequestAnnotation("/aoi")
    public List<ConcreteRole> printOnLineRole(String  mname){
        //mid地图id
        int mid = mapService.getMapIdByMapName(mname);
        //根据地图id找到对应以上线的role，返回一个list
        List<ConcreteRole> list = roleService.getOnlineRole(mid);
        return list;
    }
}
