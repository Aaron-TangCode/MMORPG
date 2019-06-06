package com.game.role.repository;

import com.game.mapper.RoleMapper;
import com.game.role.bean.ConcreteRole;
import com.game.role.task.RoleUpdateTask;
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
     * @param id
     * @return
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
     * @param roleName
     * @return
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
     * @param roleName
     * @param dest
     * @return
     */
    public void updateMap(String roleName, Integer dest) {
        RoleUpdateTask task = new RoleUpdateTask(roleName,dest);
        ThreadPoolUtils.getThreadPool().execute(task);
    }
    /**
     * 创建角色
     * @param name
     * @return
     */
    public boolean registerRole(String name) {
        SqlSession session = SqlUtils.getSession();
        try {
            RoleMapper mapper = session.getMapper(RoleMapper.class);
            return false;
        }finally {
            session.close();
        }
    }

    /**
     * 根据roleName获取mapid
     * @param roleName
     * @return
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
     * @param mapId
     * @return
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
}
