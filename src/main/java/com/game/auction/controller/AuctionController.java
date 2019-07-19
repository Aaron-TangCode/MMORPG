package com.game.auction.controller;

import com.game.auction.bean.Auction;
import com.game.auction.service.AuctionService;
import com.game.auction.task.Task;
import com.game.backpack.bean.Goods;
import com.game.backpack.controller.BackpackController;
import com.game.backpack.service.BackpackService;
import com.game.dispatcher.RequestAnnotation;
import com.game.map.threadpool.TaskQueue;
import com.game.role.bean.ConcreteRole;
import com.game.role.service.RoleService;
import com.game.user.threadpool.UserThreadPool;
import com.game.utils.MapUtils;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName AuctionController
 * @Description 拍卖行
 * @Author DELL
 * @Date 2019/7/18 10:46
 * @Version 1.0
 */
@Controller
@RequestAnnotation("/auction")
public class AuctionController {

    @Autowired
    private BackpackService backpackService;

    @Autowired
    private BackpackController backpackController;

    @Autowired
    private AuctionService auctionService;

    @Autowired
    private RoleService roleService;

    /**
     * 竞拍物品
     * @param roleName
     * @param auctionId
     * @param money
     */
    @RequestAnnotation("/bidding")
    public void bidding(String roleName,String auctionId,String money){
        //获取角色
        ConcreteRole buyRole = getRole(roleName);
        //获取交易平台的物品信息
        hanleBidding(buyRole,auctionId,money);

    }

    /**
     * 处理逻辑
     */
    private void hanleBidding(ConcreteRole buyRole,String auctionId,String money) {
        Auction auction = auctionService.queryAutionById(Integer.parseInt(auctionId));
        Integer oldMoney = auction.getPrice();
        String oldBuyer = auction.getBuyer();
        //设置新的竞拍者
        auction.setBuyer(buyRole.getName());
        //校验价格
        if(Integer.parseInt(money)<=oldMoney){
            buyRole.getCtx().channel().writeAndFlush("你的竞拍价格需要高于目前价格");
            return;
        }
        //更新价格
        auction.setPrice(Integer.parseInt(money));
        auctionService.updateAuction(auction);
        //把钱退回给其他人
        String seller = auction.getSeller();
        if(!seller.equals(oldBuyer)){
            ConcreteRole oldRole = getRole(oldBuyer);
            oldRole.setMoney(oldRole.getMoney()+oldMoney);
            roleService.updateRole(oldRole);
            oldRole.getCtx().channel().writeAndFlush("你的竞拍的物品："+auction.getGoodsName()+"竞拍价格没"+buyRole.getName()+"高,你之前的竞拍金币已退回");
        }else{
            buyRole.getCtx().channel().writeAndFlush("你成功竞拍商品："+auction.getGoodsName());
        }
    }

    /**
     * 撤回交易物品
     * @param roleName
     * @param auctionId
     */
    @RequestAnnotation("/recycle")
    public void recycle(String roleName,String auctionId){
        //获取角色
        ConcreteRole role = getRole(roleName);
        //查询auction
        Auction auction = auctionService.queryAutionById(Integer.parseInt(auctionId));
        //删掉auction
        auctionService.deleteAuction(Integer.parseInt(auctionId));
        //角色增加物品
        String goodsName = auction.getGoodsName();
        backpackController.getGoods(roleName,goodsName);
        role.getCtx().channel().writeAndFlush("成功撤回物品："+goodsName);
    }
    /**
     * 发布物品到拍卖行
     * @param seller 售卖者
     * @param goodsName 物品名称
     * @param number 物品数量
     * @param price 价钱
     * @param isNow true:一口价 ；false:定时拍卖
     */
    @RequestAnnotation("/publish")
    public void publish(String seller,String goodsName,String number,String price,String isNow){
        //获取角色
        ConcreteRole role = getRole(seller);
        //获取商品
        Goods goods = getGoods(role.getId(),goodsName);
        //发布商品
        publishGoods(role,goods,number,price,isNow);
    }

    /**
     * 发布物品
     * @param role
     * @param goods
     * @param number
     * @param price
     * @param isNow
     */
    private void publishGoods(ConcreteRole role, Goods goods, String number, String price, String isNow) {
        Auction auction = new Auction();
        auction.setGoodsName(goods.getName());
        auction.setNumber(Integer.parseInt(number));
        auction.setPrice(Integer.parseInt(price));
        auction.setSeller(role.getName());
        auction.setBuyer(role.getName());
        //一口价模式
        if(isNow.equals("true")){
            //插入数据到数据库
            auctionService.insertGoods(auction);
            //更新玩家的物品数据
            backpackController.discardGoods(role.getName(),goods.getName());
            //返回信息
            role.getCtx().channel().writeAndFlush("【一口价模式】:成功发布物品"+goods.getName());
        }else{//拍卖模式
            //上传到交易平台
            auctionService.insertGoods(auction);
            //更新玩家的物品数据
            backpackController.discardGoods(role.getName(),goods.getName());
            //查询物品
            int id = Math.abs(role.getCtx().channel().id().hashCode());
            int modIndex = id% UserThreadPool.DEFAULT_THREAD_POOL_SIZE;
            role.getCtx().channel().writeAndFlush("【竞拍模式】:成功发布物品"+goods.getName());
            //把物品放在交易平台，开始倒计时
            Task task = new Task(auction,auctionService,backpackController,roleService);
            //打包成一个任务，丢给线程池执行
            //添加任务到队列
            TaskQueue.getQueue().add(task);
            //线程执行任务
            UserThreadPool.ACCOUNT_SERVICE[modIndex].scheduleAtFixedRate( () ->{
                        Iterator<Runnable> iterator = TaskQueue.getQueue().iterator();
                        while (iterator.hasNext()) {
                            Runnable runnable = iterator.next();
                            if (Objects.nonNull(runnable)) {
                                runnable.run();
                            }
                        }

                    },
                    120L,5L, TimeUnit.SECONDS);
        }
    }

    /**
     * 获取商品
     * @param roleId
     * @param goodsName
     * @return
     */
    private Goods getGoods(int roleId,String goodsName) {
        Goods goods = null;
        //获取物品列表
        List<Goods> goodsList = backpackService.getGoodsByRoleId(roleId);
        //遍历列表，找出具体物品
        for (int i = 0; i < goodsList.size(); i++) {
            if (goodsList.get(i).getName().equals(goodsName)) {
                goods = goodsList.get(i);
                break;
            }
        }
        return goods;
    }

    /**
     * 获取角色
     * @param seller
     * @return
     */
    private ConcreteRole getRole(String seller) {
        return MapUtils.getMapRolename_Role().get(seller);
    }

    /**
     * 查看拍卖行有哪些商品
     */
    @RequestAnnotation("/queryAuction")
    public void queryAuction(String roleName){
        //获取角色
        ConcreteRole role = getRole(roleName);
        //查询所有列表
        List<Auction> auctionList = auctionService.queryAllGoods();
        //遍历数据
        printData(role,auctionList);

    }

    private void printData(ConcreteRole role, List<Auction> auctionList) {
        Channel channel = role.getCtx().channel();
        for (Auction auction : auctionList) {
            String outputContent = "id:{0}\tgoodsName:{1}\tprice:{2}\tseller:{3}\tnumber:{4}\tbuyer:{5}";
            channel.writeAndFlush(MessageFormat.format(outputContent,auction.getId(),auction.getGoodsName(),auction.getPrice(),auction.getSeller(),auction.getNumber(),auction.getBuyer()));
        }
    }
}
