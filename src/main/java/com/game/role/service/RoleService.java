package com.game.role.service;

import com.game.role.repository.RoleRepository;
import com.game.role.bean.ConcreteRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName RoleService
 * @Description 角色service
 * @Author DELL
 * @Date 2019/5/2920:47
 * @Version 1.0
 */
@Service("RoleService")
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    /**
     * 获取角色role
     * @param id
     * @return
     */
    public ConcreteRole getRole(int id){
        return roleRepository.getRole(id);
    }

    /**
     * 根据角色名roleName获取地图map
     * @param roleName
     * @return
     */
    public String getMapByRoleName(String roleName) {
        return roleRepository.getMapByRoleName(roleName);
    }

    /**
     * 更新角色所在的地图
     * @param roleName
     * @param dest
     * @return
     */
    public boolean updateMap(String roleName, Integer dest) {
        return roleRepository.updateMap(roleName,dest);
    }


    /**
     * 创建角色role
     * @param name
     * @return
     */
    public boolean registerRole(String name) {
        return roleRepository.registerRole(name);
    }

    /**
     * 根据角色名roleName获取地图id
     * @param roleName
     * @return
     */
    public int getMapIdByRoleName(String roleName) {
        return roleRepository.getMapIdByRoleName(roleName);
    }

    /**
     * 根据地图id获取地图name
     * @param map_id
     * @return
     */
    public String getMapNameByMapId(int map_id) {
        return roleRepository.getMapNameByMapId(map_id);
    }
}
