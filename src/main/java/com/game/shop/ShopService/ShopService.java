package com.game.shop.ShopService;

import com.game.backpack.bean.Goods;
import com.game.backpack.controller.BackpackController;
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
 * @Description TODO
 * @Author DELL
 * @Date 2019/8/12 18:20
 * @Version 1.0
 */
@Service
public class ShopService {
    @Autowired
    private RoleService roleService;
    @Autowired
    private BackpackController backpackController;
    public MsgShopInfoProto.ResponseShopInfo buy(Channel channel, MsgShopInfoProto.RequestShopInfo requestShopInfo) {
        //获取role
        ConcreteRole role = getRole(channel);
        //goodsName
        String goodsName = requestShopInfo.getGoodsName();
        //从商店获取商品
        Goods goods = ShopManager.getShopMap().get(goodsName);
        //购物
        Integer money = role.getMoney();

        Integer cost = goods.getCost();

        role.setMoney(money-cost);
        //消耗玩家金币
        roleService.updateRole(role);
        //获得商品
        backpackController.getGoods(role.getName(),goodsName);

        String content =  role.getName()+"成功购买"+goodsName;
        return MsgShopInfoProto.ResponseShopInfo.newBuilder()
                .setContent(content)
                .setType(MsgShopInfoProto.RequestType.BUY)
                .build();
    }

    public ConcreteRole getRole(Channel channel){
        Integer userId = LocalUserMap.getChannelUserMap().get(channel);
        ConcreteRole role = LocalUserMap.getUserRoleMap().get(userId);
        return role;
    }
}
