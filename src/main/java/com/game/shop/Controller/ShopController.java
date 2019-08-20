package com.game.shop.Controller;

import com.game.backpack.bean.Goods;
import com.game.backpack.controller.BackpackController;
import com.game.dispatcher.RequestAnnotation;
import com.game.role.bean.ConcreteRole;
import com.game.role.service.RoleService;
import com.game.shop.manager.ShopManager;
import com.game.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName ShopController
 * @Description 商店控制器
 * @Author DELL
 * @Date 2019/7/1 20:57
 * @Version 1.0
 */
@RequestAnnotation("/shop")
@Component
public class ShopController {
    /**
     * 角色服务
     */
    @Autowired
    private RoleService roleService;
    /**
     * 背包控制器
     */
    @Autowired
    private BackpackController backpackController;

    /**
     * 购买物品
     * @param roleName 角色名
     * @param goodsName 物品名
     * @return 信息
     */
    @RequestAnnotation("/buy")
    public String buy(String roleName,String goodsName){
        //获取玩家
        ConcreteRole role = MapUtils.getMapRolename_Role().get(roleName);
        //从商店获取商品
        Goods goods = ShopManager.getShopMap().get(goodsName);
        //购物
        Integer money = role.getMoney();

        Integer cost = goods.getCost();

        role.setMoney(money-cost);
        //消耗玩家金币
        roleService.updateRole(role);
        //获得商品
        backpackController.getGoods(roleName,goodsName);

        return roleName+"成功购买"+goodsName;
    }
}
