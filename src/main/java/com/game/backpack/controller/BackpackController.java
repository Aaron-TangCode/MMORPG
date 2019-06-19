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

    /**
     * 逻辑处理
     * @param goodsName 物品名
     * @param role 角色
     * @param roleName 角色名
     * @return 字符串
     */
    private String handleGoods(String goodsName,ConcreteRole role,String roleName) {
        //在数据库查询，根据角色id查询是否具有物品
        List<Goods> list = backpackService.getGoodsByRoleId(role.getId());
        //找物品
        Goods goods =findGoods(list,goodsName);
        //选择添加或更新方式
        String msg = chooseWay(list,goods,roleName,goodsName,role);
        //返回信息
        return msg;
    }

    /**
     * 选择增加或更新物品方式
     * @param list 角色已拥有的物品列表
     * @param goods 物品
     * @param roleName 角色名
     * @param goodsName 物品名
     * @param role 角色
     * @return 字符串
     */
    private String chooseWay(List<Goods> list,Goods goods,String roleName,String goodsName,ConcreteRole role) {
        //角色拥有物品的数量
        int existedGoods = list.size();

        //不存在，就增加一行;存在且物品数量已满，就增加一行
        boolean flag = (goods==null||goods.getCount()==Goods.GOODS_MAXCOUNT)&&(existedGoods<Goods.ROLE_MAXGOODS);
        boolean flag2 = goods!=null&&goods.getCount()<Goods.GOODS_MAXCOUNT&&existedGoods<Goods.ROLE_MAXGOODS;
        boolean flag3 = goods!=null&&goods.getCount()<Goods.GOODS_MAXCOUNT&&existedGoods==Goods.ROLE_MAXGOODS;
        if(flag){
            //在本地缓存拿装备的详细信息,在数据库中没信息，依赖roleName在本地缓存中查询
            Goods localGoods = MapUtils.getGoodsMap().get(goodsName);
            if(localGoods==null){
                return "装备不存在本地缓存";
            }
            localGoods.setRoleId(role.getId());
            backpackService.insertGoods(localGoods);
            return roleName+"获得物品："+goodsName;
        }else if (flag2){
            if(goods.getType()>1){
                //如果物品是装备，不能叠加，直接添加
                backpackService.insertGoods(goods);
            }else{
                //存在且物品数量未满，就更新数量
                backpackService.updateGoodsByRoleId(role.getId(),goods.getId());
            }
            return roleName+"获得物品："+goodsName;
        }else if(flag3){
            if(goods.getType()>1){
                return roleName+"的背包已满";
            }else{
                //存在且物品数量未满，就更新数量
                backpackService.updateGoodsByRoleId(role.getId(),goods.getId());
                return roleName+"获得物品："+goodsName;
            }
        }else {
            //如果获得的物品在背包中不存在或叠加数已满，且没有剩余的格子则返回背包已满的提示
            return roleName+"的背包已满";
        }
    }

    /**
     * 匹配物品
     * @param list
     * @param goodsName
     * @return
     */
    private Goods findGoods(List<Goods> list,String goodsName) {
        Goods goods = null;
        //遍历物品
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getName().equals(goodsName)){
                goods = list.get(i);
            }
        }
        return goods;
    }

    /**
     * 返回角色
     * @param roleName
     * @return
     */
    private ConcreteRole getRole(String roleName) {
        return MapUtils.getMapRolename_Role().get(roleName);
    }

    /**
     * 丢掉物品
     * todo
     * @return
     */
    public String discardGoods(){
        return null;
    }
}
