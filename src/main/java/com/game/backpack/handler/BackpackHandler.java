package com.game.backpack.handler;

import com.game.backpack.bean.Goods;
import com.game.backpack.service.BackpackService;
import com.game.dispatcher.RequestAnnotation;
import com.game.role.bean.ConcreteRole;
import com.game.utils.CacheUtils;
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
public class BackpackHandler {

    @Autowired
    private BackpackService backpackService;

    /**
     * 获取物品
     * @param roleName 角色名
     * @param goodsName 物品名称
     * @return 返回消息
     */
    @RequestAnnotation("/getGoods")
    public String getGoods(String roleName,String goodsName){
        //获取角色
        ConcreteRole role = getRole(roleName);
        //物品逻辑处理
        String msg = handleGoods(goodsName,role);
        //返回消息
        return msg;
    }

    /**
     * 逻辑处理
     * @param goodsName 物品名
     * @param role 角色
     * @return 字符串
     */
    private String handleGoods(String goodsName,ConcreteRole role) {
        //在数据库查询，根据角色id查询是否具有物品
        List<Goods> list = backpackService.getGoodsByRoleId(role.getId());
        //找物品(数据库)
        Goods goods_db =findGoods(list,goodsName);
        //找物品（本地缓存）
        Goods goods_local = CacheUtils.getGoodsMap().get(goodsName);
        //更新本地的count信息
        if(goods_db!=null){
            goods_db.setRepeat(goods_local.getRepeat());
        }
        //选择添加或更新方式
        String msg = chooseWay(list,goods_db,goodsName,role);
        //返回信息
        return msg;
    }

    /**
     * 选择增加或更新物品方式
     * @param list 角色已拥有的物品列表
     * @param goods 物品
     * @param goodsName 物品名
     * @param role 角色
     * @return 字符串
     */
    private String chooseWay(List<Goods> list,Goods goods,String goodsName,ConcreteRole role) {
        //角色拥有物品的数量
        int existedGoods = list.size();
        //不存在或物品数量已满且背包未满，就增加
        boolean flag1  = (goods==null||goods.getCount()==goods.getRepeat())&&(existedGoods<role.getBackpackCapacity());
        //存在且物品数量未满
        boolean flag2 = goods!=null&&goods.getCount()<goods.getRepeat();

        if(flag1){
            //在本地缓存拿装备的详细信息,在数据库中没信息，依赖roleName在本地缓存中查询
            Goods localGoods = CacheUtils.getGoodsMap().get(goodsName);
            if(localGoods==null){
                return "装备不存在本地缓存";
            }
            localGoods.setRoleId(role.getId());
            backpackService.insertGoods(localGoods);
            return role.getName()+"获得物品："+goodsName;
        }else if (flag2){
            //存在且物品数量未满，就更新数量
            backpackService.updateGoodsByRoleId(role.getId(),goods.getId());
            return role.getName()+"获得物品："+goodsName;
        } else {
            //如果获得的物品在背包中不存在或叠加数已满，且没有剩余的格子则返回背包已满的提示
            return role.getName()+"的背包已满";
        }
    }

    /**
     * 匹配物品
     * @param list 物品列表
     * @param goodsName 物品名称
     * @return 物品
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
     * @param roleName 角色名
     * @return 返回角色
     */
    private ConcreteRole getRole(String roleName) {
        return CacheUtils.getMapRolename_Role().get(roleName);
    }

    /**
     * 丢掉物品
     * @param roleName 角色名
     * @param goodsName 物品名
     * @return 返回消息
     */
    public String discardGoods(String roleName,String goodsName){
        ConcreteRole role = getRole(roleName);
        //逻辑处理
        String msg = discardHandle(role,goodsName);
        return msg;
    }

    /**
     * 丢弃物品
     * @param role 角色
     * @param goodsName 物品名
     * @return 返回消息
     */
    private String discardHandle(ConcreteRole role, String goodsName) {
        //在数据库查询，根据角色id查询是否具有物品
        List<Goods> list = backpackService.getGoodsByRoleId(role.getId());
        //找物品(数据库)
        Goods goods_db =findGoods(list,goodsName);
        //找物品（本地缓存）
        Goods goods_local = CacheUtils.getGoodsMap().get(goodsName);
        //更新本地的count信息
        if(goods_db!=null){
            goods_db.setRepeat(goods_local.getRepeat());
        }
        //选择丢弃
        String msg = discardWay(goods_db,goodsName,role);
        //返回信息
        return msg;
    }

    /**
     * 丢弃物品
     * @param goods 物品
     * @param goodsName 物品名
     * @param role 角色
     * @return 返回消息
     */
    private String discardWay(Goods goods, String goodsName, ConcreteRole role) {
        //物品数量-1
        backpackService.updateGoodsByRoleIdDel(role.getId(),goods.getId());
        return role.getName()+"的"+goodsName+"数量-1";
    }
}
