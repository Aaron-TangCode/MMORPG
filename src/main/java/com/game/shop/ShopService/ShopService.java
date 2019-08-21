package com.game.shop.ShopService;

import com.game.backpack.bean.Goods;
import com.game.backpack.handler.BackpackHandler;
import com.game.protobuf.protoc.MsgShopInfoProto;
import com.game.role.bean.ConcreteRole;
import com.game.role.service.RoleService;
import com.game.shop.manager.ShopManager;
import com.game.user.manager.LocalUserMap;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName ShopService
 * @Description 商店服务
 * @Author DELL
 * @Date 2019/8/12 18:20
 * @Version 1.0
 */
@Service
public class ShopService {
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
     * 购物
     * @param channel channel
     * @param requestShopInfo 商店请求信息
     * @return 协议信息
     */
    public MsgShopInfoProto.ResponseShopInfo buy(Channel channel, MsgShopInfoProto.RequestShopInfo requestShopInfo) {
        //获取role
        ConcreteRole role = getRole(channel);
        //goodsName
        String goodsName = requestShopInfo.getGoodsName();
        //从商店获取商品
        Goods goods = ShopManager.getShopMap().get(goodsName);
        //购物
        Integer money = role.getMoney();
        //物品价格
        Integer cost = goods.getCost();
        //花钱
        role.setMoney(money-cost);
        //消耗玩家金币
        roleService.updateRole(role);
        //获得商品
        backpackHandler.getGoods(role.getName(),goodsName);
        //信息
        String content =  role.getName()+"成功购买"+goodsName;
        //返回消息
        return MsgShopInfoProto.ResponseShopInfo.newBuilder()
                .setContent(content)
                .setType(MsgShopInfoProto.RequestType.BUY)
                .build();
    }

    /**
     * 获取角色
     * @param channel channel
     * @return role
     */
    public ConcreteRole getRole(Channel channel){
        Integer userId = LocalUserMap.getChannelUserMap().get(channel);
        ConcreteRole role = LocalUserMap.getUserRoleMap().get(userId);
        return role;
    }
}
