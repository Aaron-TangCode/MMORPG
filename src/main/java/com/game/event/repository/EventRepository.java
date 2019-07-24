package com.game.event.repository;

import com.game.event.mapper.RoleCountMapper;
import com.game.utils.SqlUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

/**
 * @ClassName EventRepository
 * @Description TODO
 * @Author DELL
 * @Date 2019/7/23 12:01
 * @Version 1.0
 */
@Repository
public class EventRepository {

    public int queryCount(int roleId) {
        SqlSession session = SqlUtils.getSession();
        try{
            RoleCountMapper mapper = session.getMapper(RoleCountMapper.class);
            int count = mapper.queryCount(roleId);
            return count;
        }finally {
            session.close();
        }
    }

    public void updateCount(int count,int roleId) {
        SqlSession session = SqlUtils.getSession();
        try{
            RoleCountMapper mapper = session.getMapper(RoleCountMapper.class);
            mapper.updateCount(count,roleId);
            session.commit();
        }finally {
            session.close();
        }
    }

    public void insert(int roleId, int count) {
        SqlSession session = SqlUtils.getSession();
        try{
            RoleCountMapper mapper = session.getMapper(RoleCountMapper.class);
           mapper.insert(roleId,count);
           session.commit();
        }finally {
            session.close();
        }
    }
}
