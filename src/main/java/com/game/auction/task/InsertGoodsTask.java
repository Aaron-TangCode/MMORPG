package com.game.auction.task;

import com.game.auction.bean.Auction;
import com.game.auction.mapper.AuctionMapper;
import com.game.utils.SqlUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.concurrent.Callable;

/**
 * @ClassName InsertGoodsTask
 * @Description 插入物品任务
 * @Author DELL
 * @Date 2019/8/19 10:46
 * @Version 1.0
 */
public class InsertGoodsTask implements Callable {
    private Auction auction;

    public InsertGoodsTask(Auction auction) {
        this.auction = auction;
    }

    /**
     * 插入拍卖物品
     * @return
     * @throws Exception
     */
    @Override
    public Object call() throws Exception {
        SqlSession session = SqlUtils.getSession();
        try{
            AuctionMapper mapper = session.getMapper(AuctionMapper.class);
            Integer integer = mapper.insertGoods(auction);
            session.commit();
            return integer;
        }finally {
            session.close();
        }
    }
}
