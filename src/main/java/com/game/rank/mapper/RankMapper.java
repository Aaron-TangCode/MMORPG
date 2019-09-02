package com.game.rank.mapper;

import com.game.rank.bean.RankBean;

import java.util.List;

/**
 * @ClassName RankMapper
 * @Description 排行榜mapper
 * @Author DELL
 * @Date 2019/8/5 16:45
 * @Version 1.0
 */
public interface RankMapper {
    /**
     * 查询排行榜
     * @return list
     */
    public List<RankBean> queryRank();
}
