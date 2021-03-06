package com.game.role.mapper;

import com.game.role.bean.ConcreteRole;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName RoleMapper
 * @Description 游戏角色mapper
 * @Author DELL
 * @Date 2019/5/2920:50
 * @Version 1.0
 */
public interface RoleMapper {
    /**
     * 根据id获取role实体类
     * @param id id
     * @return role
     */
    public ConcreteRole getRoleById(Integer id);

    /**
     * 根据角色名roleName获取地图
     * @param roleName rolename
     * @return map
     */
    public String getMapByRoleName(@Param("roleName") String roleName);

    /**
     * 更新某个游戏角色的所在地图
     * @param roleName rolename
     * @param dest dest
     * @return true or false
     */
    public boolean updateMap(@Param("roleName") String roleName, @Param("dest") Integer dest);

    /**
     * 创建游戏角色
     * @param name name
     * @return true or false
     */
    public boolean registerRole(String name);

    /**
     * 通过角色名获取地图id
     * @param roleName rolenam
     * @return int
     */
    public Integer getMapIdByRoleName(String roleName);

    /**
     * 通过地图id获取地图name
     * @param id id
     * @return map's name
     */
    public String getMapNameByMapId(Integer id);

    /**
     * 通过rolename获取role
     * @param rolename rolename
     * @return role
     */
    public ConcreteRole getRoleByRoleName(String rolename);

    /**
     * 更新role
     * @param concreteRole role
     */
    public void updateRole(ConcreteRole concreteRole);

    /**
     * 插入role
     * @param role role
     */
    public void insertRole(ConcreteRole role);

}
