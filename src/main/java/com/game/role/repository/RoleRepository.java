package com.game.role.repository;

import com.game.role.mapper.RoleMapper;
import com.game.role.bean.ConcreteRole;
import com.game.role.task.InsertRoleTask;
import com.game.role.task.RoleUpdateTask;
import com.game.skill.task.RoleTask;
import com.game.user.threadpool.UserThreadPool;
import com.game.utils.SqlUtils;
import com.game.utils.ThreadPoolUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

/**
 * @ClassName RoleRepository
 * @Description 角色repository
 * @Author DELL
 * @Date 2019/5/2920:41
 * @Version 1.0
 */
@Repository("RoleRepository")
public class RoleRepository {
    /**
     * 获取角色
     * @param id id
     * @return role
     */
    public ConcreteRole getRole(int id) {
        SqlSession session = SqlUtils.getSession();
        try {
            RoleMapper mapper = session.getMapper(RoleMapper.class);
            ConcreteRole role = mapper.getRoleById(id);
            return role;
        }finally {
            session.close();
        }
    }

    /**
     * 通过角色名roleName获取地图map
     * @param roleName rolename
     * @return map's name
     */
    public String getMapByRoleName(String roleName) {
        SqlSession session = SqlUtils.getSession();
        try {
            RoleMapper mapper = session.getMapper(RoleMapper.class);
            String mapName = mapper.getMapByRoleName(roleName);
            return mapName;
        }finally {
            session.close();
        }
    }

    /**
     * 更新角色所在地图
     * @param roleName rolename
     * @param dest dest
     */
    public void updateMap(String roleName, Integer dest) {
        RoleUpdateTask task = new RoleUpdateTask(roleName,dest);
        ThreadPoolUtils.getThreadPool().execute(task);
    }


    /**
     * 根据roleName获取mapid
     * @param roleName rolename
     * @return int
     */
    public int getMapIdByRoleName(String roleName) {
        SqlSession session = SqlUtils.getSession();
        try {
            RoleMapper mapper = session.getMapper(RoleMapper.class);
            Integer id = mapper.getMapIdByRoleName(roleName);
            return id;
        }finally {
            session.close();
        }
    }

    /**
     * 根据id获取name
     * @param mapId mapId
     * @return map's name
     */
    public String getMapNameByMapId(int mapId) {
        SqlSession session = SqlUtils.getSession();
        try {
            RoleMapper mapper = session.getMapper(RoleMapper.class);
            String name = mapper.getMapNameByMapId(mapId);
            return name;
        }finally {
            session.close();
        }
    }

    /**
     * 根据rolename获取role
     * @param rolename rolename
     * @return role
     */
    public ConcreteRole getRoleByRoleName(String rolename) {
        SqlSession session = SqlUtils.getSession();
        try {
            RoleMapper mapper = session.getMapper(RoleMapper.class);
            ConcreteRole role = mapper.getRoleByRoleName(rolename);
            return role;
        }finally {
            session.close();
        }
    }

    /**
     * 更新角色
     * @param concreteRole role
     */
    public void updateRole(ConcreteRole concreteRole) {
        RoleTask roleTask = new RoleTask(concreteRole);
        ThreadPoolUtils.getThreadPool().execute(roleTask);
    }

    /**
     * 插入角色
     * @param role role
     */
    public void insertRole(ConcreteRole role) {
        InsertRoleTask insertRoleTask = new InsertRoleTask(role);
        UserThreadPool.ACCOUNT_SERVICE[0].submit(insertRoleTask);
    }
}
