package com.game.role.repository;

import com.game.mapper.RoleMapper;
import com.game.role.bean.ConcreteRole;
import com.game.utils.SqlUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName RoleRepository
 * @Description TODO
 * @Author DELL
 * @Date 2019/5/2920:41
 * @Version 1.0
 */
@Repository("RoleRepository")
public class RoleRepository {

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

    public boolean updateMap(String roleName, Integer dest) {
        SqlSession session = SqlUtils.getSession();
        try {
            RoleMapper mapper = session.getMapper(RoleMapper.class);
            boolean isSuccess = mapper.updateMap(roleName,dest);
            session.commit();
            return isSuccess;
        }finally {
            session.close();
        }
    }

    public List<ConcreteRole> getOnlineRole(int mid) {
        SqlSession session = SqlUtils.getSession();
        try {
            RoleMapper mapper = session.getMapper(RoleMapper.class);
            List<ConcreteRole> list = mapper.getOnlineRole(mid);
            return list;
        }finally {
            session.close();
        }
    }

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
     * 根据is获取name
     * @param map_id
     * @return
     */
    public String getMapNameByMapId(int map_id) {
        SqlSession session = SqlUtils.getSession();
        try {
            RoleMapper mapper = session.getMapper(RoleMapper.class);
            String name = mapper.getMapNameByMapId(map_id);
            return name;
        }finally {
            session.close();
        }
    }
}
