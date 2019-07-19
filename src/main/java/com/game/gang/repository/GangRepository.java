package com.game.gang.repository;

import com.game.gang.bean.GangEntity;
import com.game.gang.bean.GangMemberEntity;
import com.game.gang.mapper.GangEntityMapper;
import com.game.gang.mapper.GangMemberEntityMapper;
import com.game.utils.SqlUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

/**
 * @ClassName GangRepository
 * @Description TODO
 * @Author DELL
 * @Date 2019/7/16 16:22
 * @Version 1.0
 */
@Repository
public class GangRepository {
    /**
     * 查询工会成员
     * @param roleId
     * @return
     */
    public GangMemberEntity findGangMember(int roleId) {
        SqlSession session = SqlUtils.getSession();
        try {
            GangMemberEntityMapper mapper = session.getMapper(GangMemberEntityMapper.class);
            GangMemberEntity gangMember = mapper.findGangMember(roleId);
            return gangMember;
        }finally {
            session.close();
        }
    }

    /**
     * 添加工会
     * @param gangName
     */
    public void insertGang(String gangName) {
        SqlSession session = SqlUtils.getSession();
        try {
            GangEntityMapper mapper = session.getMapper(GangEntityMapper.class);
            mapper.insertGang(gangName);
            session.commit();
        }finally {
            session.close();
        }
    }

    /**
     * 查询工会
     * @param gangName
     * @return
     */
    public GangEntity queryGang(String gangName) {
        SqlSession session = SqlUtils.getSession();
        try {
            GangEntityMapper mapper = session.getMapper(GangEntityMapper.class);
            GangEntity gangEntity = mapper.queryGang(gangName);
            return gangEntity;
        }finally {
            session.close();
        }
    }

    /**
     * 添加工会成员
     * @param entity
     */
    public void insertGangMember(GangMemberEntity entity) {
        SqlSession session = SqlUtils.getSession();
        try {
            GangMemberEntityMapper mapper = session.getMapper(GangMemberEntityMapper.class);
            mapper.insertGangMember(entity);
            session.commit();
        }finally {
            session.close();
        }
    }

    public GangEntity queryGangByRoleName(Integer roleId) {
        SqlSession session = SqlUtils.getSession();
        try {
            GangEntityMapper mapper = session.getMapper(GangEntityMapper.class);
            GangEntity gangEntity = mapper.queryGangByRoleName(roleId);
            return gangEntity;
        }finally {
            session.close();
        }

    }

    public void updateGangEntity(GangEntity gangEntity) {
        SqlSession session = SqlUtils.getSession();
        try {
            GangEntityMapper mapper = session.getMapper(GangEntityMapper.class);
            mapper.updateGangEntity(gangEntity);
            session.commit();
        }finally {
            session.close();
        }
    }
}
