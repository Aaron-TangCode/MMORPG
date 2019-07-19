package com.game.auction.repository;

import com.game.auction.bean.Auction;
import com.game.auction.mapper.AuctionMapper;
import com.game.utils.SqlUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName AuctionRepository
 * @Description TODO
 * @Author DELL
 * @Date 2019/7/18 11:08
 * @Version 1.0
 */
@Repository
public class AuctionRepository {
    /**
     * 发行物品
     * @param auction
     */
    public Integer insertGoods(Auction auction) {
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
     * @param auctionId
     */
    public void deleteAuction(int auctionId) {
        SqlSession session = SqlUtils.getSession();
        try{
            AuctionMapper mapper = session.getMapper(AuctionMapper.class);
            mapper.deleteAuction(auctionId);
            session.commit();
        }finally {
            session.close();
        }
    }

    /**
     * 更新交易平台
     * @param auction
     */
    public void updateAuction(Auction auction) {
        SqlSession session = SqlUtils.getSession();
        try{
            AuctionMapper mapper = session.getMapper(AuctionMapper.class);
            mapper.updateAuction(auction);
            session.commit();
        }finally {
            session.close();
        }
    }
}
