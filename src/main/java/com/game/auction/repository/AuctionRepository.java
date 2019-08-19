package com.game.auction.repository;

import com.game.auction.bean.Auction;
import com.game.auction.mapper.AuctionMapper;
import com.game.auction.task.DeleteAuctionTask;
import com.game.auction.task.InsertGoodsTask;
import com.game.auction.task.UpdateAuctionTask;
import com.game.user.threadpool.UserThreadPool;
import com.game.utils.SqlUtils;
import io.netty.util.concurrent.Future;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @ClassName AuctionRepository
 * @Description 拍卖行数据层
 * @Author DELL
 * @Date 2019/7/18 11:08
 * @Version 1.0
 */
@Repository
public class AuctionRepository {
    /**
     * 添加物品
     * @param auction 拍卖行
     */
    public Integer insertGoods(Auction auction) {
        //创建任务
        InsertGoodsTask insertGoodsTask = new InsertGoodsTask(auction);
        //提交任务到线程池
        Future submit = UserThreadPool.ACCOUNT_SERVICE[0].submit(insertGoodsTask);
        Integer integer = null;
        try {
            integer = (Integer) submit.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //返回值
        return integer;
    }

    /**
     * 查询所有物品
     * @return
     */
    public List<Auction> queryAllGoods() {
        SqlSession session = SqlUtils.getSession();
        try{
            AuctionMapper mapper = session.getMapper(AuctionMapper.class);
            List<Auction> auctionList = mapper.queryAllGoods();
            return auctionList;
        }finally {
            session.close();
        }
    }

    /**
     *  根据id查询交易会物品
     * @param auctionId
     * @return
     */
    public Auction queryAutionById(int auctionId) {
        SqlSession session = SqlUtils.getSession();
        try{
            AuctionMapper mapper = session.getMapper(AuctionMapper.class);
            Auction auction = mapper.queryAutionById(auctionId);
            return auction;
        }finally {
            session.close();
        }
    }

    /**
     * 删除auction
     * @param auctionId 拍卖唯一Id
     */
    public void deleteAuction(int auctionId) {
        //创建任务
        DeleteAuctionTask deleteAuctionTask = new DeleteAuctionTask(auctionId);
        //任务提交到线程池
        UserThreadPool.ACCOUNT_SERVICE[0].submit(deleteAuctionTask);
    }

    /**
     * 更新交易平台
     * @param auction 拍卖行
     */
    public void updateAuction(Auction auction) {
        //创建任务
        UpdateAuctionTask updateAuctionTask = new UpdateAuctionTask(auction);
        //任务提交到线程池
        UserThreadPool.ACCOUNT_SERVICE[0].submit(updateAuctionTask);
    }
}
