package com.game.auction.task;

import com.game.auction.bean.Auction;
import com.game.auction.mapper.AuctionMapper;
import com.game.utils.SqlUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.concurrent.Callable;

/**
 * @ClassName UpdateAuctionTask
 * @Description 更新拍卖行任务
 * @Author DELL
 * @Date 2019/8/19 11:10
 * @Version 1.0
 */
public class UpdateAuctionTask implements Callable {
    private Auction auction;

    public UpdateAuctionTask(Auction auction) {
        this.auction = auction;
    }

    /**
     * 更新拍卖物品
     * @return 返回Obj
     * @throws Exception 异常
     */
    @Override
    public Object call() throws Exception {
        SqlSession session = SqlUtils.getSession();
        try{
            AuctionMapper mapper = session.getMapper(AuctionMapper.class);
            mapper.updateAuction(auction);
            session.commit();
        }finally {
            session.close();
        }
        return null;
    }
}
