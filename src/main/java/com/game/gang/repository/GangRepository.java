package com.game.gang.repository;

import com.game.gang.bean.GangEntity;
import com.game.gang.bean.GangMemberEntity;
import com.game.gang.mapper.GangEntityMapper;
import com.game.gang.mapper.GangMemberEntityMapper;
import com.game.gang.task.InsertGangMemberTask;
import com.game.gang.task.InsertGangTask;
import com.game.gang.task.UpdateGangEntityTask;
import com.game.user.threadpool.UserThreadPool;
import com.game.utils.SqlUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

/**
 * @ClassName GangRepository
 * @Description 工会数据访问
 * @Author DELL
 * @Date 2019/7/16 16:22
 * @Version 1.0
 */
@Repository
public class GangRepository {
    /**
     * 查询工会成员
     * @param roleId 角色id
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
     * @param gangName 工会名字
     */
    public void insertGang(String gangName) {
        InsertGangTask insertGangTask = new InsertGangTask(gangName);
        UserThreadPool.ACCOUNT_SERVICE[0].submit(insertGangTask);
    }

    /**
     * 查询工会
     * @param gangName 工会名字
     * @return 工会
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
     * @param entity 工会成员
     */
    public void insertGangMember(GangMemberEntity entity) {
        InsertGangMemberTask insertGangMemberTask = new InsertGangMemberTask(entity);
        UserThreadPool.ACCOUNT_SERVICE[0].submit(insertGangMemberTask);
    }

    /**
     * 查询工会
     * @param roleId 角色id
     * @return 工会
     */
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

    /**
     * 更新工会
     * @param gangEntity 工会
     */
    public void updateGangEntity(GangEntity gangEntity) {
        UpdateGangEntityTask updateGangEntityTask = new UpdateGangEntityTask(gangEntity);
        UserThreadPool.ACCOUNT_SERVICE[0].submit(updateGangEntityTask);
    }
}
