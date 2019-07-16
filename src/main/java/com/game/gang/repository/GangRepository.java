package com.game.gang.repository;

import com.game.gang.bean.GangMemberEntity;
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

}
