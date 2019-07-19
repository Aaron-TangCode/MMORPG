package com.game.auction.mapper;

import com.game.auction.bean.Auction;

import java.util.List;

public interface AuctionMapper {
    public Integer insertGoods(Auction auction);
    public List<Auction> queryAllGoods();
    public Auction queryAutionById(int auctionId);
    public void deleteAuction(int auctionId);
    public void updateAuction(Auction auction);
}
