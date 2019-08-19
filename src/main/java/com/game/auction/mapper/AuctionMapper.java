package com.game.auction.mapper;

import com.game.auction.bean.Auction;

import java.util.List;

/**
 * 拍卖行mapper
 */
public interface AuctionMapper {
    /**
     * 添加物品
     * @param auction 拍卖行
     * @return
     */
    public Integer insertGoods(Auction auction);

    /**
     * 查询所有拍卖物品
     * @return
     */
    public List<Auction> queryAllGoods();

    /**
     * 根据id查询拍卖物品
     * @param auctionId 拍卖唯一id
     * @return
     */
    public Auction queryAutionById(int auctionId);

    /**
     * 下架拍卖物品或物品竞拍结束
     * @param auctionId 拍卖唯一Id
     */
    public void deleteAuction(int auctionId);

    /**
     * 更新拍卖
     * @param auction 拍卖唯一id
     */
    public void updateAuction(Auction auction);
}
