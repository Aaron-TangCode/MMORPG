package com.game.team.repository;

import com.game.team.mapper.TeamMapper;
import com.game.utils.SqlUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

/**
 * @ClassName TeamRepository
 * @Description 队伍数据访问
 * @Author DELL
 * @Date 2019/8/23 12:22
 * @Version 1.0
 */
@Repository
public class TeamRepository {
    /**
     * 创建队伍
     * @param teamName teamName
     * @param roleId roleId
     */
    public void addTeam(String teamName, Integer roleId) {
        SqlSession session = SqlUtils.getSession();
        try{
            TeamMapper mapper = session.getMapper(TeamMapper.class);
            mapper.addTeam(teamName, roleId);
            session.commit();
        }finally {
            session.close();
        }

    }
}
