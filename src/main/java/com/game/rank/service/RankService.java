package com.game.rank.service;

import com.game.rank.bean.RankBean;
import com.game.rank.repository.RankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName RankService
 * @Description TODO
 * @Author DELL
 * @Date 2019/8/5 16:20
 * @Version 1.0
 */
@Service
public class RankService {
    @Autowired
    private RankRepository rankRepository;

    public List<RankBean> queryRank() {
        return rankRepository.queryRank();
    }
}
