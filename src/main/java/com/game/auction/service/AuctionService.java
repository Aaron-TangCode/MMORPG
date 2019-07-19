package com.game.auction.service;

import com.game.auction.bean.Auction;
import com.game.auction.repository.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName AuctionService
 * @Description TODO
 * @Author DELL
 * @Date 2019/7/18 11:08
 * @Version 1.0
 */
@Service
public class AuctionService {
    @Autowired
    private AuctionRepository auctionRepository;

    public Integer insertGoods(Auction auction) {
       return auctionRepository.insertGoods(auction);
    }

    public List<Auction> queryAllGoods() {
        return auctionRepository.queryAllGoods();
    }

    public Auction queryAutionById(int auctionId) {
        return auctionRepository.queryAutionById(auctionId);
    }

    public void deleteAuction(int auctionId) {
        auctionRepository.deleteAuction(auctionId);
    }

    public void updateAuction(Auction auction) {
        auctionRepository.updateAuction(auction);
    }
}
