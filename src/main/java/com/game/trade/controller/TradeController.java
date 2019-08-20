package com.game.trade.controller;

import com.game.backpack.bean.Goods;
import com.game.backpack.controller.BackpackController;
import com.game.dispatcher.RequestAnnotation;
import com.game.role.bean.ConcreteRole;
import com.game.role.service.RoleService;
import com.game.trade.bean.Trade;
import com.game.trade.manager.TradeMap;
import com.game.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.UUID;

/**
 * @ClassName TradeController
 * @Description 交易控制器
 * @Author DELL
 * @Date 2019/7/15 12:28
 * @Version 1.0
 */
@RequestAnnotation("/trade")
public class TradeController {
    @Autowired
    private BackpackController backpackController;

    @Autowired
    private RoleService roleService;

    /**
     * 请求交易
     * @param roleName1 rolename1
     * @param roleName2 rolename2
     */
    @RequestAnnotation("/requestTrade")
    public void requestTrade(String roleName1,String roleName2){
        //生成交易ID
        String uuid = UUID.randomUUID().toString();
        //发起交易请求
        ConcreteRole from = getRole(roleName1);
        ConcreteRole to = getRole(roleName2);
        //构建交易Bean
        Trade trade = new Trade(uuid,from,to);
        TradeMap.getTradeMap().put(uuid,trade);
        to.getChannel().writeAndFlush(roleName1+":请求交易"+"("+uuid+")");
        from.getChannel().writeAndFlush("等待"+roleName2+"确认交易");
    }

    /**
     * 确认交易
     * @param roleName1 roleName1
     * @param roleName2 roleName2
     * @param uuid uuid
     */
    @RequestAnnotation("/confirmTrade")
    public void confirmTrade(String roleName1,String roleName2,String uuid){
        ConcreteRole to = getRole(roleName2);
        ConcreteRole from = getRole(roleName1);

        from.getChannel().writeAndFlush("进入交易("+uuid+")");
        to.getChannel().writeAndFlush("进入交易("+uuid+")");
    }

    /**
     * 交易物品
     * @param uuid uuid
     * @param goodsName 物品名称
     */
    @RequestAnnotation("/tradingGoods")
    public void tradingGoods(String uuid,String goodsName){
        Trade trade = TradeMap.getTradeMap().get(uuid);

        ConcreteRole from = trade.getFrom();
        ConcreteRole to = trade.getTo();

        //获取物品
        Goods goods = MapUtils.getGoodsMap().get(goodsName);

        boolean success = bigDeal(from, to, goods);

        String msg = success?"成功交易":"交易失败";

        from.getChannel().writeAndFlush(msg);
        to.getChannel().writeAndFlush(msg);

    }
    @RequestAnnotation("/tradingMoney")
    public void  tradingMoney(String uuid,String number){
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

        from.getChannel().writeAndFlush(msg);
        to.getChannel().writeAndFlush(msg);

        //释放交易
        TradeMap.getTradeMap().remove(uuid);
    }
    /**
     * 获取角色
     * @param roleName
     * @return
     */
    public ConcreteRole getRole(String roleName){
        return MapUtils.getMapRolename_Role().get(roleName);
    }
    /**
     * 玩家和玩家之间交易
     * @param roleName1
     * @param roleName2
     * @param goodsName
     * @return
     */
    @Deprecated
    @RequestAnnotation("/tradegoods")
    public String trade(String roleName1,String roleName2,String goodsName){
        //获取玩家1、获取玩家2
        Map<String, ConcreteRole> roleMap = MapUtils.getMapRolename_Role();
        ConcreteRole seller = roleMap.get(roleName1);
        ConcreteRole buyer = roleMap.get(roleName2);
        //获取物品
        Map<String, Goods> goodsMap = MapUtils.getGoodsMap();
        Goods goods = goodsMap.get(goodsName);
        //交易物品
        boolean success = bigDeal(seller,buyer,goods);
        //交易完成
        if(success){
            return roleName1+"和"+roleName2+"成功交易";
        }else{
            return roleName1+"和"+roleName2+"交易失败";
        }
    }

    /**
     * 处理交易逻辑
     * @param seller
     * @param buyer
     * @param goods
     * @return
     */
    private boolean bigDeal(ConcreteRole seller, ConcreteRole buyer, Goods goods) {
        try {
            //玩家1：物品减少，增加金币
            backpackController.getGoods(seller.getName(),goods.getName());
            seller.setMoney(seller.getMoney()+goods.getCost());
            roleService.updateRole(seller);
            //玩家2：物品增加，减少金币
           backpackController.discardGoods(buyer.getName(),goods.getName());
            buyer.setMoney(buyer.getMoney()-goods.getCost());
            roleService.updateRole(buyer);
            return true;
        }catch (Exception e){
            throw new RuntimeException();
        }
    }
}
