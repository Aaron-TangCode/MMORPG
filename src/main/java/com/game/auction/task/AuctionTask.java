package com.game.auction.task;

import com.game.auction.bean.Auction;
import com.game.auction.service.AuctionService;
import com.game.backpack.handler.BackpackMsgHandler;
import com.game.protobuf.protoc.MsgAuctionInfoProto;
import com.game.role.bean.ConcreteRole;
import com.game.role.service.RoleService;
import com.game.utils.CacheUtils;
import org.springframework.stereotype.Component;

/**
 * @ClassName DispatcherTask
 * @Description 交易平台的定时任务
 * @Author DELL
 * @Date 2019/7/19 11:04
 * @Version 1.0
 */
@Component
public class AuctionTask implements  Runnable{
    /**
     * 拍卖行
     */
    private Auction auction;
    /**
     * 拍卖服务
     */
    private AuctionService auctionService;
    /**
     * 背包处理器
     */
    private BackpackMsgHandler backpackHandler;
    /**
     * 角色服务
     */
    private RoleService roleService;

    public AuctionTask(Auction auction, AuctionService auctionService, BackpackMsgHandler backpackHandler, RoleService roleService){
        this.auction = auction;
        this.auctionService = auctionService;
        this.backpackHandler = backpackHandler;
        this.roleService = roleService;
    }

    public AuctionTask(){}
    @Override
    public void run() {
        //根据id查Auction
        Auction auction = auctionService.queryAutionById(this.auction.getId());
        //生产者
        String seller = auction.getSeller();
        ConcreteRole sellRole = CacheUtils.getRole(seller);
        //购买者
        String buyer = auction.getBuyer();
        ConcreteRole buyRole = CacheUtils.getRole(buyer);

        //把物品给购买者
        backpackHandler.getGoods(buyer,auction.getGoodsName());

        //移除任务
        sellRole.getQueue().remove();
        //返回信息
        if(buyer.equals(seller)){
            //删除竞拍数据
            auctionService.deleteAuction(this.auction.getId());
            String content = "物品没人购买，已退回";
            MsgAuctionInfoProto.ResponseAuctionInfo info = MsgAuctionInfoProto.ResponseAuctionInfo.newBuilder()
                    .setType(MsgAuctionInfoProto.RequestType.BIDDING)
                    .setContent(content)
                    .build();
            sellRole.getChannel().writeAndFlush(info);
        }else{
            //删除竞拍数据
            auctionService.deleteAuction(this.auction.getId());
            //更新生产者的钱
            Integer money = sellRole.getMoney();
            Integer price = auction.getPrice();
            sellRole.setMoney(money+price);
            roleService.updateRole(sellRole);

            String buyContent = "成功拍到物品："+auction.getGoodsName();
            String sellContent = "物品成功售出，获得金币："+price;
            MsgAuctionInfoProto.ResponseAuctionInfo buyInfo = MsgAuctionInfoProto.ResponseAuctionInfo.newBuilder()
                    .setType(MsgAuctionInfoProto.RequestType.BIDDING)
                    .setContent(buyContent)
                    .build();
            buyRole.getChannel().writeAndFlush(buyInfo);
            MsgAuctionInfoProto.ResponseAuctionInfo sellInfo = MsgAuctionInfoProto.ResponseAuctionInfo.newBuilder()
                    .setType(MsgAuctionInfoProto.RequestType.BIDDING)
                    .setContent(sellContent)
                    .build();

            sellRole.getChannel().writeAndFlush(sellInfo);
        }

    }
}
