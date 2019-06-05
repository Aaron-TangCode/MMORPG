package com.game.role.service;

import com.game.role.repository.RoleRepository;
import com.game.role.bean.ConcreteRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName RoleService
 * @Description TODO
 * @Author DELL
 * @Date 2019/5/2920:47
 * @Version 1.0
 */
@Service("RoleService")
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public ConcreteRole getRole(int id){
        return roleRepository.getRole(id);
    }

    public String getMapByRoleName(String roleName) {
        return roleRepository.getMapByRoleName(roleName);
    }

    public boolean updateMap(String roleName, Integer dest) {
        return roleRepository.updateMap(roleName,dest);
    }

    public List<ConcreteRole> getOnlineRole(int mid) {
        return roleRepository.getOnlineRole(mid);
    }

    public boolean registerRole(String name) {
        return roleRepository.registerRole(name);
    }

    public int getMapIdByRoleName(String roleName) {
        return roleRepository.getMapIdByRoleName(roleName);
    }

    public String getMapNameByMapId(int map_id) {
        return roleRepository.getMapNameByMapId(map_id);
    }
}
