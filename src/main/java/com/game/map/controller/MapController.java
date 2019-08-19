package com.game.map.controller;

import com.game.dispatcher.RequestAnnotation;
import com.game.map.bean.ConcreteMap;
import com.game.role.bean.ConcreteRole;
import com.game.map.service.MapService;
import com.game.role.service.RoleService;
import com.game.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName MapController
 * @Description 地图控制类
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

    /**
     * 获取当前地图
     * @param id
     * @return
     */
    @RequestAnnotation("/getMap")
    public ConcreteMap getMap(int id){
        return mapService.getMap(id);
    }

    /**
     * 切换地图
     * @param roleName
     * @param dest
     * @return
     */
    @RequestAnnotation("/moveTo")
    public String moveTo(String roleName,String dest) {
        //获取角色信息
        ConcreteRole role = MapUtils.getMapRolename_Role().get(roleName);
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
            role.getConcreteMap().setId(dest_id);
            //更新本地缓存
            MapUtils.getMapRolename_Role().put(roleName,role);
            return role.getName()+"从"+src+"移动到"+dest;
        }else{
            return "不能从"+src+"直接移动到"+dest;
        }
    }

}
