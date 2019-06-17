package com.game.backpack.controller;

import com.game.backpack.bean.Goods;
import com.game.backpack.service.BackpackService;
import com.game.dispatcher.RequestAnnotation;
import com.game.role.bean.ConcreteRole;
import com.game.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName BackpackController
 * @Description 背包控制器
 * @Author DELL
 * @Date 2019/6/17 15:09
 * @Version 1.0
 */
@Component
@RequestAnnotation("/backpack")
public class BackpackController {

    @Autowired
    private BackpackService backpackService;

    @RequestAnnotation("/getGoods")
    public String getGoods(String roleName,String goodsName){
        //获取角色
        ConcreteRole role = getRole(roleName);
        //逻辑处理
        String msg = handleGoods(goodsName,role,roleName);
        return msg;
    }
    private String handleGoods(String goodsName,ConcreteRole role,String roleName) {
        //根据角色id查询是否具有物品
        List<Goods> list = backpackService.getGoodsByRoleId(role.getId());
        Goods goods = null;
        //遍历物品
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getName().equals(goodsName)){
                goods = list.get(i);
            }
        }
        //角色拥有物品的数量
        int existedGoods = list.size();
        //不存在，就增加一行;存在且物品数量已满，就增加一行
        boolean flag = (goods==null||goods.getCount()==Goods.GOODS_MAXCOUNT)&&(existedGoods<Goods.ROLE_MAXGOODS);
        if(flag){
            Goods newGoods = new Goods();
            newGoods.setCount(1);
            newGoods.setName(goodsName);
            newGoods.setRoleId(role.getId());
            backpackService.insertGoods(newGoods);
            return roleName+"获得物品："+goodsName;
        }else if (goods!=null&&goods.getCount()<Goods.GOODS_MAXCOUNT){
            //存在且物品数量未满，就更新数量
            backpackService.updateGoodsByRoleId(role.getId(),goods.getId());
            return roleName+"获得物品："+goodsName;
        }else {
            //如果获得的物品在背包中不存在或叠加数已满，且没有剩余的格子则返回背包已满的提示
            return roleName+"的背包已满";
        }
    }


    private ConcreteRole getRole(String roleName) {
        return MapUtils.getMapRolename_Role().get(roleName);
    }

    public String discardGoods(){
        return null;
    }
}
