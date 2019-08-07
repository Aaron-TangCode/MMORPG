package com.game.rank.repository;

import com.game.rank.bean.RankBean;
import com.game.rank.mapper.RankMapper;
import com.game.utils.SqlUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName RankRepository
 * @Description TODO
 * @Author DELL
 * @Date 2019/8/5 16:20
 * @Version 1.0
 */
@Repository
public class RankRepository {

    public List<RankBean> queryRank() {
        SqlSession session = SqlUtils.getSession();
        RankMapper mapper = session.getMapper(RankMapper.class);
        List<RankBean> rankBeanList = mapper.queryRank();
        return rankBeanList;
    }
}
