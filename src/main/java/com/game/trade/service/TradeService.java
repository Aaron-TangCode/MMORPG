package com.game.trade.service;

import com.game.backpack.bean.Goods;
import com.game.backpack.handler.BackpackHandler;
import com.game.protobuf.protoc.MsgTradeInfoProto;
import com.game.role.bean.ConcreteRole;
import com.game.role.service.RoleService;
import com.game.trade.bean.Trade;
import com.game.trade.manager.TradeMap;
import com.game.user.manager.LocalUserMap;
import com.game.utils.MapUtils;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

/**
 * @ClassName TradeService
 * @Description 交易服务
 * @Author DELL
 * @Date 2019/8/13 12:18
 * @Version 1.0
 */
@Service
public class TradeService {
    @Autowired
    private BackpackHandler backpackHandler;
    @Autowired
    private RoleService roleService;

    /**
     * 请求交易
     * @param channel Channel
     * @param requestTradeInfo requestTradeInfo
     * @return 协议信息
     */
    public MsgTradeInfoProto.ResponseTradeInfo requestTrade(Channel channel, MsgTradeInfoProto.RequestTradeInfo requestTradeInfo) {
        //生成交易ID
        String uuid = UUID.randomUUID().toString();
        //发起交易请求
        ConcreteRole from = getRoleByChannel(channel);
        //rolename
        String roleName2 = requestTradeInfo.getRoleName();
        ConcreteRole to = getRoleByRoleName(roleName2);
        //构建交易Bean
        Trade trade = new Trade(uuid,from,to);
        TradeMap.getTradeMap().put(uuid,trade);
        String msg = from.getName()+":请求交易"+"("+uuid+")";
        MsgTradeInfoProto.ResponseTradeInfo info = MsgTradeInfoProto.ResponseTradeInfo.newBuilder()
                .setContent(msg)
                .setType(MsgTradeInfoProto.RequestType.REQUESTTRADE)
                .build();

        to.getChannel().writeAndFlush(info);
        String content = "等待"+roleName2+"确认交易";
        return MsgTradeInfoProto.ResponseTradeInfo.newBuilder()
                .setContent(content)
                .setType(MsgTradeInfoProto.RequestType.REQUESTTRADE)
                .build();
    }

    /**
     * 确认交易
     * @param channel channel
     * @param requestTradeInfo requestTradeInfo
     * @return 协议信息
     */
    public MsgTradeInfoProto.ResponseTradeInfo confirmTrade(Channel channel, MsgTradeInfoProto.RequestTradeInfo requestTradeInfo) {
        //rolename
        String roleName2 = requestTradeInfo.getRoleName();
        //uuid
        String uuid = requestTradeInfo.getUuid();
        ConcreteRole to = getRoleByRoleName(roleName2);

        //内容d
        String content = "进入交易("+uuid+")";
        MsgTradeInfoProto.ResponseTradeInfo info = MsgTradeInfoProto.ResponseTradeInfo.newBuilder()
                .setContent(content)
                .setType(MsgTradeInfoProto.RequestType.CONFIRMTRADE)
                .build();
        to.getChannel().writeAndFlush(info);
        //返回消息
        return MsgTradeInfoProto.ResponseTradeInfo.newBuilder()
                .setContent(content)
                .setType(MsgTradeInfoProto.RequestType.CONFIRMTRADE)
                .build();
    }

    /**
     * 交易物品
     * @param channel channel
     * @param requestTradeInfo requestTradeInfo
     * @return 协议信息
     */
    public MsgTradeInfoProto.ResponseTradeInfo tradingGoods(Channel channel, MsgTradeInfoProto.RequestTradeInfo requestTradeInfo) {
        //uuid
        String uuid = requestTradeInfo.getUuid();
        //goodsname
        String goodsName = requestTradeInfo.getGoodsName();
        Trade trade = TradeMap.getTradeMap().get(uuid);

        ConcreteRole from = trade.getFrom();
        ConcreteRole to = trade.getTo();

        //获取物品
        Goods goods = MapUtils.getGoodsMap().get(goodsName);

        boolean success = bigDeal(from, to, goods);
        //内容
        String msg = success?"成功交易":"交易失败";
        //协议信息
        MsgTradeInfoProto.ResponseTradeInfo info = MsgTradeInfoProto.ResponseTradeInfo.newBuilder()
                .setContent(msg)
                .setType(MsgTradeInfoProto.RequestType.TRADINGGOODS)
                .build();

        from.getChannel().writeAndFlush(info);
        to.getChannel().writeAndFlush(info);

        return MsgTradeInfoProto.ResponseTradeInfo.newBuilder()
                .setContent(msg)
                .setType(MsgTradeInfoProto.RequestType.TRADINGGOODS)
                .build();
    }

    /**
     * 价格
     * @param channel channel
     * @param requestTradeInfo requestTradeInfo
     * @return 协议信息
     */
    public MsgTradeInfoProto.ResponseTradeInfo tradingMoney(Channel channel, MsgTradeInfoProto.RequestTradeInfo requestTradeInfo) {
        //uuid
        String uuid = requestTradeInfo.getUuid();
        //number
        String number = requestTradeInfo.getNumber();
        Trade trade = TradeMap.getTradeMap().get(uuid);
        //获取角色
        ConcreteRole from = trade.getFrom();
        ConcreteRole to = trade.getTo();
        //获取金币
        Integer fromMoney = from.getMoney();
        Integer toMoney = to.getMoney();
        //处理
        Integer cost = Integer.parseInt(number);
        if(fromMoney>=cost){
            from.setMoney(fromMoney-cost);
            to.setMoney(toMoney+cost);
        }


        roleService.updateRole(from);
        roleService.updateRole(to);

        String msg = "成功交易";

        MsgTradeInfoProto.ResponseTradeInfo info = MsgTradeInfoProto.ResponseTradeInfo.newBuilder()
                .setType(MsgTradeInfoProto.RequestType.TRADINGMONEY)
                .setContent(msg)
                .build();

        to.getChannel().writeAndFlush(info);

        //释放交易
        TradeMap.getTradeMap().remove(uuid);

        return MsgTradeInfoProto.ResponseTradeInfo.newBuilder()
                .setType(MsgTradeInfoProto.RequestType.TRADINGMONEY)
                .setContent(msg)
                .build();
    }

    /**
     * 交易信息
     * @param channel channel
     * @param requestTradeInfo requestTradeInfo
     * @return 协议信息
     */
    public MsgTradeInfoProto.ResponseTradeInfo tradeGoods(Channel channel, MsgTradeInfoProto.RequestTradeInfo requestTradeInfo) {
        //goodsName
        String goodsName = requestTradeInfo.getGoodsName();
        //rolename2
        String roleName2 = requestTradeInfo.getRoleName();
        //获取玩家1、获取玩家2
        ConcreteRole seller = getRoleByChannel(channel);
        ConcreteRole buyer = getRoleByRoleName(roleName2);
        //获取物品
        Map<String, Goods> goodsMap = MapUtils.getGoodsMap();
        Goods goods = goodsMap.get(goodsName);
        //交易物品
        boolean success = bigDeal(seller,buyer,goods);
        //交易完成
        String content = null;
        if(success){
            content =  seller.getName()+"和"+roleName2+"成功交易";
        }else{
            content =  seller.getName()+"和"+roleName2+"交易失败";
        }
        return MsgTradeInfoProto.ResponseTradeInfo.newBuilder()
                .setContent(content)
                .setType(MsgTradeInfoProto.RequestType.TRADEGOODS)
                .build();
    }

    /**
     * 获取角色
     * @param channel channel
     * @return 协议信息
     */
    public ConcreteRole getRoleByChannel(Channel channel){
        Integer userId = LocalUserMap.getChannelUserMap().get(channel);

        ConcreteRole role = LocalUserMap.getUserRoleMap().get(userId);
        return role;
    }

    /**
     * 获取角色
     * @param roleName 角色名
     * @return role
     */
    public ConcreteRole getRoleByRoleName(String roleName){
        ConcreteRole role = MapUtils.getMapRolename_Role().get(roleName);
        return role;
    }
    /**
     * 处理交易逻辑
     * @param seller 卖家
     * @param buyer 买家
     * @param goods 物品
     * @return 消息
     */
    private boolean bigDeal(ConcreteRole seller, ConcreteRole buyer, Goods goods) {
        try {
            //玩家1：物品减少，增加金币
            backpackHandler.getGoods(seller.getName(),goods.getName());
            seller.setMoney(seller.getMoney()+goods.getCost());
            roleService.updateRole(seller);
            //玩家2：物品增加，减少金币
            backpackHandler.discardGoods(buyer.getName(),goods.getName());
            buyer.setMoney(buyer.getMoney()-goods.getCost());
            roleService.updateRole(buyer);
            return true;
        }catch (Exception e){
            throw new RuntimeException();
        }
    }
}
