package com.game.auction.task;

import com.game.auction.mapper.AuctionMapper;
import com.game.utils.SqlUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.concurrent.Callable;

/**
 * @ClassName DeleteAuctionTask
 * @Description 删除拍卖行任务
 * @Author DELL
 * @Date 2019/8/19 11:07
 * @Version 1.0
 */
public class DeleteAuctionTask implements Callable {
    private int auctionId;

    public DeleteAuctionTask(int auctionId) {
        this.auctionId = auctionId;
    }

    /**
     * 删除拍卖任务
     * @return
     * @throws Exception
     */
    @Override
    public Object call() throws Exception {
        SqlSession session = SqlUtils.getSession();
        try{
            AuctionMapper mapper = session.getMapper(AuctionMapper.class);
            mapper.deleteAuction(auctionId);
            session.commit();
        }finally {
            session.close();
        }
        return null;
    }
}
