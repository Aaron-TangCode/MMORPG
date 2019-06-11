package com.game.role.controller;

import com.game.dispatcher.RequestAnnotation;
import com.game.role.bean.ConcreteRole;
import com.game.role.service.RoleService;
import com.game.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @ClassName RoleController
 * @Description 角色控制器
 * @Author DELL
 * @Date 2019/5/2917:58
 * @Version 1.0
 */
@RequestAnnotation("/role")
@Controller
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 进入世界
     */
    @RequestAnnotation("/enterMap")
    public void enterMap(){
        System.out.println("enterMap");
    }

    /**
     * 获取角色role所在地图
     * @param roleName
     * @return
     */
    @RequestAnnotation("/getMap")
    public String getMap(String roleName){
        //根据roleName查找map_id
        int map_id = roleService.getMapIdByRoleName(roleName);
        //根据map_id查找地图
        String map_name = roleService.getMapNameByMapId(map_id);
        //返回值
        return roleName+"目前在"+map_name;
    }

    /**
     * 角色受到伤害
     * @param roleName
     * @return
     */
    @RequestAnnotation("/roleByHurted")
    public String roleByHurted(String roleName){
        ConcreteRole concreteRole = MapUtils.getMapRolename_Role().get(roleName);
        if(concreteRole==null){
            return roleName+"还没登录，请先登录";
        }
        if(concreteRole.getHp()>0){
            if(concreteRole.getHp()-10>0){
                concreteRole.setHp(concreteRole.getHp()-10);
                return roleName+"受到伤害，生命值减10";
            }else{
                concreteRole.setHp(0);
                return roleName+"受到伤害，生命值减为0";
            }
        }else{
            return "已死亡";
        }
    }

    /**
     * 角色加血
     * @param roleName
     * @return
     */
    @RequestAnnotation("/roleByAddBlood")
    public String roleByAddBlood(String roleName){
        ConcreteRole concreteRole = MapUtils.getMapRolename_Role().get(roleName);
        if(concreteRole==null){
            return roleName+"还没登录，请先登录";
        }
        if(concreteRole.getHp()>=100){
            return "已满血";
        }else{
            if(concreteRole.getHp()+10>=100){
                concreteRole.setHp(100);
            }else{
                concreteRole.setHp(concreteRole.getHp()+10);
            }
            return roleName+"的血量已加10Hp";
        }
    }

    /**
     * 通过rolename获取角色状态信息
     * @param roleName
     * @return
     */
    @RequestAnnotation("/getRoleState")
    public String getRoleState(String roleName){
        ConcreteRole concreteRole = MapUtils.getMapRolename_Role().get(roleName);
        if(concreteRole==null){
            return roleName+"还没登录，请先登录";
        }
        return concreteRole.getHp()>0?"生存":"死亡";
    }
}
