package com.game.mapper;

import com.game.role.ConcreteRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName RoleMapper
 * @Description TODO
 * @Author DELL
 * @Date 2019/5/2920:50
 * @Version 1.0
 */
public interface RoleMapper {
    public ConcreteRole getRoleById(Integer id);

    public String getMapByRoleName(@Param("roleName") String roleName);

    public boolean updateMap(@Param("roleName") String roleName, @Param("dest") Integer dest);

    public List<ConcreteRole> getOnlineRole(int mid);

    public boolean registerRole(String name);

    public Integer getMapIdByRoleName(String roleName);

    public String getMapNameByMapId(Integer id);
}
