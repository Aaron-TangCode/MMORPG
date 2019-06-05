package com.game.controller;

import com.game.dispatcher.RequestAnnotation;
import com.game.service.RoleService;
import com.game.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @ClassName RoleController
 * @Description TODO
 * @Author DELL
 * @Date 2019/5/2917:58
 * @Version 1.0
 */
@RequestAnnotation("/role")
@Controller
public class RoleController {
    @Autowired
    private RoleService roleService;



    @RequestAnnotation("/enterMap")
    public void enterMap(){
        System.out.println("enterMap");
    }
    @RequestAnnotation("/getMap")
    public String getMap(String roleName){
        //根据roleName查找map_id
        int map_id = roleService.getMapIdByRoleName(roleName);
        //根据map_id查找地图
        String map_name = roleService.getMapNameByMapId(map_id);
        //返回值
        return roleName+"目前在"+map_name;
    }
    @RequestAnnotation("/roleByHurted")
    public String roleByHurted(String roleName){
        return null;
    }

    @RequestAnnotation("/roleByAddBlood")
    public String roleByAddBlood(String roleName){
        return null;
    }
//    @RequestAnnotation("/register")
//    public String register(String name){
//        //
//       return roleService.registerRole(name);
//    }
}
