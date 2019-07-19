package com.game.auction.task;

import com.game.auction.bean.Auction;
import com.game.auction.service.AuctionService;
import com.game.backpack.controller.BackpackController;
import com.game.map.threadpool.TaskQueue;
import com.game.role.bean.ConcreteRole;
import com.game.role.service.RoleService;
import com.game.utils.MapUtils;
import org.springframework.stereotype.Component;

/**
 * @ClassName Task
 * @Description 交易平台的定时任务
 * @Author DELL
 * @Date 2019/7/19 11:04
 * @Version 1.0
 */
@Component
public class Task implements  Runnable{
    private Auction auction;

    private AuctionService auctionService;

    private BackpackController backpackController;

    private RoleService roleService;

    public Task(Auction auction,AuctionService auctionService,BackpackController backpackController,RoleService roleService){
        this.auction = auction;
        this.auctionService = auctionService;
        this.backpackController = backpackController;
        this.roleService = roleService;
    }

    public Task(){}
    @Override
    public void run() {
        //根据id查Auction
        Auction auction = auctionService.queryAutionById(this.auction.getId());
        //生产者
        String seller = auction.getSeller();
        ConcreteRole sellRole = getRole(seller);
        //购买者
        String buyer = auction.getBuyer();
        ConcreteRole buyRole = getRole(buyer);

        //把物品给购买者
        backpackController.getGoods(buyer,auction.getGoodsName());

        //更新生产者的钱
        Integer money = sellRole.getMoney();
        Integer price = auction.getPrice();
        sellRole.setMoney(money+price);
        roleService.updateRole(sellRole);
        //移除任务
        TaskQueue.getQueue().remove();
        //返回信息
        if(buyer.equals(seller)){
            sellRole.getCtx().channel().writeAndFlush("物品没人购买，已退回");
        }else{
            buyRole.getCtx().channel().writeAndFlush("成功拍到物品："+auction.getGoodsName());
            sellRole.getCtx().channel().writeAndFlush("物品成功售出，获得金币："+price);
        }

    }
    public ConcreteRole getRole(String roleName){
        return MapUtils.getMapRolename_Role().get(roleName);
    }
}
