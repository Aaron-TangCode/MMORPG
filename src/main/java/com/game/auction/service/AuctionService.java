package com.game.auction.service;

import com.game.auction.bean.Auction;
import com.game.auction.repository.AuctionRepository;
import com.game.auction.task.AuctionTask;
import com.game.backpack.bean.Goods;
import com.game.backpack.handler.BackpackHandler;
import com.game.backpack.service.BackpackService;
import com.game.map.threadpool.TaskQueue;
import com.game.protobuf.protoc.MsgAuctionInfoProto;
import com.game.role.bean.ConcreteRole;
import com.game.role.service.RoleService;
import com.game.user.manager.LocalUserMap;
import com.game.user.threadpool.UserThreadPool;
import com.game.utils.MapUtils;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName AuctionService
 * @Description 拍卖服务
 * @Author DELL
 * @Date 2019/7/18 11:08
 * @Version 1.0
 */
@Service
public class AuctionService {
    /**
     * 拍卖数据访问
     */
    @Autowired
    private AuctionRepository auctionRepository;
    /**
     * 角色服务
     */
    @Autowired
    private RoleService roleService;
    /**
     * 背包控制器
     */
    @Autowired
    private BackpackHandler backpackHandler;
    /**
     * 背包服务
     */
    @Autowired
    private BackpackService backpackService;

    /**
     * 添加物品
     * @param auction 拍卖行
     * @return 整数
     */
    public Integer insertGoods(Auction auction) {
       return auctionRepository.insertGoods(auction);
    }

    /**
     * 查询所有物品
     * @return 整数
     */
    public List<Auction> queryAllGoods() {
        return auctionRepository.queryAllGoods();
    }

    /**
     * 通过id查询拍卖
     * @param auctionId 拍卖id
     * @return 返回拍卖实体
     */
    public Auction queryAutionById(int auctionId) {
        return auctionRepository.queryAutionById(auctionId);
    }

    /**
     * 删除拍卖物品
     * @param auctionId 拍卖id
     */
    public void deleteAuction(int auctionId) {
        auctionRepository.deleteAuction(auctionId);
    }

    /**
     * 更新拍卖
     * @param auction 拍卖
     */
    public void updateAuction(Auction auction) {
        auctionRepository.updateAuction(auction);
    }

    /**
     * 竞拍
     * @param channel channel
     * @param requestAuctionInfo 拍卖请求信息
     * @return 返回拍卖协议信息
     */
    public MsgAuctionInfoProto.ResponseAuctionInfo bidding(Channel channel, MsgAuctionInfoProto.RequestAuctionInfo requestAuctionInfo) {
        //获取角色
        ConcreteRole buyRole = getRole(channel);
        //auctionId
        String auctionId = requestAuctionInfo.getAuctionId();
        //money
        String money = requestAuctionInfo.getMoney();
        //获取交易平台的物品信息
        String content = hanleBidding(buyRole,auctionId,money);
        //返回协议信息
        return MsgAuctionInfoProto.ResponseAuctionInfo.newBuilder()
                .setContent(content)
                .setType(MsgAuctionInfoProto.RequestType.BIDDING)
                .build();
    }

    /**
     * 下架拍卖物品
     * @param channel channel
     * @param requestAuctionInfo 拍卖请求信息
     * @return 返回拍卖协议信息
     */
    public MsgAuctionInfoProto.ResponseAuctionInfo recycle(Channel channel, MsgAuctionInfoProto.RequestAuctionInfo requestAuctionInfo) {
        //获取角色
        ConcreteRole role = getRole(channel);
        //auctionId
        String auctionId = requestAuctionInfo.getAuctionId();
        //查询auction
        Auction auction = queryAutionById(Integer.parseInt(auctionId));
        //删掉auction
        deleteAuction(Integer.parseInt(auctionId));
        //角色增加物品
        String goodsName = auction.getGoodsName();
        backpackHandler.getGoods(role.getName(),goodsName);
        String content = "成功撤回物品："+goodsName;
        return MsgAuctionInfoProto.ResponseAuctionInfo.newBuilder()
                .setType(MsgAuctionInfoProto.RequestType.RECYCLE)
                .setContent(content)
                .build();
    }

    /**
     * 发布物品或上架物品
     * @param channel channel
     * @param requestAuctionInfo 拍卖请求信息
     * @return 拍卖协议信息
     */
    public MsgAuctionInfoProto.ResponseAuctionInfo publish(Channel channel, MsgAuctionInfoProto.RequestAuctionInfo requestAuctionInfo) {
        //获取角色
        ConcreteRole role = getRole(channel);
        //goodsName
        String goodsName = requestAuctionInfo.getGoodsName();
        //number
        String number = requestAuctionInfo.getNumber();
        //money
        String money = requestAuctionInfo.getMoney();
        //isNow
        String isNow = requestAuctionInfo.getIsNow();
        //获取商品
        Goods goods = getGoods(role.getId(),goodsName);
        //发布商品
        String content = publishGoods(role,goods,number,money,isNow);
        return MsgAuctionInfoProto.ResponseAuctionInfo.newBuilder()
                .setContent(content)
                .setType(MsgAuctionInfoProto.RequestType.PUBLISH)
                .build();
    }

    /**
     * 查询拍卖物品
     * @param channel channel
     * @param requestAuctionInfo 拍卖请求信息2
     * @return 返回拍卖协议信息
     */
    public MsgAuctionInfoProto.ResponseAuctionInfo queryAuction(Channel channel, MsgAuctionInfoProto.RequestAuctionInfo requestAuctionInfo) {
        //获取角色
        ConcreteRole role = getRole(channel);
        //查询所有列表
        List<Auction> auctionList = queryAllGoods();
        //遍历数据
        String content = printData(role,auctionList);
        return MsgAuctionInfoProto.ResponseAuctionInfo.newBuilder()
                .setType(MsgAuctionInfoProto.RequestType.QUERYAUCTION)
                .setContent(content)
                .build();
    }

    /**
     * 打印数量
     * @param role 角色
     * @param auctionList 拍卖行物品列表
     * @return 字符串
     */
    private String printData(ConcreteRole role, List<Auction> auctionList) {
        Channel channel = role.getChannel();
        String content = null;
        for (Auction auction : auctionList) {
            String outputContent = "id:{0}\tgoodsName:{1}\tprice:{2}\tseller:{3}\tnumber:{4}\tbuyer:{5}\n";
           content = MessageFormat.format(outputContent,auction.getId(),auction.getGoodsName(),auction.getPrice(),auction.getSeller(),auction.getNumber(),auction.getBuyer());
        }
        return content;
    }

    /**
     * 获取角色
     * @param channel Channel
     * @return 返回角色
     */
    public ConcreteRole getRole(Channel channel){
        Integer userId = LocalUserMap.getChannelUserMap().get(channel);
        ConcreteRole role = LocalUserMap.getUserRoleMap().get(userId);
        return role;
    }

    /**
     * 获取角色
     * @param name 角色名
     * @return 返回角色
     */
    public ConcreteRole getRoleByRoleName(String name){
       return MapUtils.getMapRolename_Role().get(name);
    }

    /**
     * 处理竞拍物品
     * @param buyRole 买家
     * @param auctionId 拍卖id
     * @param money 竞拍价格
     * @return
     */
    private String hanleBidding(ConcreteRole buyRole,String auctionId,String money) {
        //获取action
        Auction auction = queryAutionById(Integer.parseInt(auctionId));
        Integer oldMoney = auction.getPrice();
        String oldBuyer = auction.getBuyer();
        //设置新的竞拍者
        auction.setBuyer(buyRole.getName());
        //校验价格
        if(Integer.parseInt(money)<=oldMoney){
            return "你的竞拍价格需要高于目前价格";
        }
        //更新价格
        auction.setPrice(Integer.parseInt(money));
        updateAuction(auction);
        //把钱退回给其他人
        String seller = auction.getSeller();
        if(!seller.equals(oldBuyer)){
            ConcreteRole oldRole = getRoleByRoleName(oldBuyer);
            oldRole.setMoney(oldRole.getMoney()+oldMoney);
            roleService.updateRole(oldRole);

            return "你的竞拍的物品："+auction.getGoodsName()+"竞拍价格没"+buyRole.getName()+"高,你之前的竞拍金币已退回";
        }else{
            return "你成功竞拍商品："+auction.getGoodsName();
        }
    }
    /**
     * 获取商品
     * @param roleId 角色id
     * @param goodsName 物品名称
     * @return 返回物品
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
     * 发布物品
     * @param role 角色
     * @param goods 物品
     * @param number 数量
     * @param price 价格
     * @param isNow true:一口价模式、false:竞拍模式
     */
    private String publishGoods(ConcreteRole role, Goods goods, String number, String price, String isNow) {
        //创建竞拍
        Auction auction = new Auction();
        //注入数据
        auction.setGoodsName(goods.getName());
        auction.setNumber(Integer.parseInt(number));
        auction.setPrice(Integer.parseInt(price));
        auction.setSeller(role.getName());
        auction.setBuyer(role.getName());
        //一口价模式
        if(isNow.equals("true")){
            //插入数据到数据库
            insertGoods(auction);
            //更新玩家的物品数据
            backpackHandler.discardGoods(role.getName(),goods.getName());
            //返回信息
            return "【一口价模式】:成功发布物品"+goods.getName();
        }else{//拍卖模式
            //上传到交易平台
            insertGoods(auction);
            //更新玩家的物品数据
            backpackHandler.discardGoods(role.getName(),goods.getName());
            //查询物品

            int threadIndex = UserThreadPool.getThreadIndex(role.getChannel().id());

            //把物品放在交易平台，开始倒计时
            AuctionTask task = new AuctionTask(auction,this, backpackHandler,roleService);
            //打包成一个任务，丢给线程池执行
            //添加任务到队列
            TaskQueue.getQueue().add(task);
            //线程执行任务
            UserThreadPool.getThreadPool(threadIndex).scheduleAtFixedRate( () ->{
                        Iterator<Runnable> iterator = TaskQueue.getQueue().iterator();
                        while (iterator.hasNext()) {
                            Runnable runnable = iterator.next();
                            if (Objects.nonNull(runnable)) {
                                runnable.run();
                            }
                        }

                    },
                    120L,5L, TimeUnit.SECONDS);
            return  "【竞拍模式】:成功发布物品"+goods.getName();
        }
    }
}
