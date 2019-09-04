package com.game.backpack.handler;

import com.game.backpack.bean.GoodsBag;
import com.game.backpack.bean.GoodsResource;
import com.game.backpack.manager.LocalGoodsManager;
import com.game.backpack.service.BackpackService;
import com.game.role.bean.ConcreteRole;
import com.game.utils.CacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public String getGoods(String roleName,String goodsName){
        //获取角色
        ConcreteRole role = getRole(roleName);
        //物品逻辑处理
//        String msg = handleGoods(goodsName,role);
        String msg = getGoodsHandler(goodsName, role);
        //返回消息
        return msg;
    }

    private String getGoodsHandler(String goodsName,ConcreteRole role) {
        //根据角色id查询是否具有物品
        Map<Integer, List<GoodsBag>> localGoodsMap = LocalGoodsManager.getLocalGoodsMap();
        //物品列表
        List<GoodsBag> goodsBagList = localGoodsMap.get(role.getId());
        //找具体物品
        GoodsResource goodsResource = findGoodsResource(goodsBagList,goodsName);
       //选择添加或更新
        String msg = chooseWay2HandleGoods(goodsResource,goodsBagList,role,goodsName);
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
        List<GoodsResource> list = backpackService.getGoodsByRoleId(role.getId());
        //找物品(数据库)
        GoodsResource goods_db =findGoods(list,goodsName);
        //找物品（本地缓存）
        GoodsResource goods_local = CacheUtils.getGoodsMap().get(goodsName);
        //更新本地的count信息
        if(goods_db!=null){
            goods_db.setRepeat(goods_local.getRepeat());
        }
        //选择添加或更新方式
        String msg = chooseWay(list,goods_db,goodsName,role);
        //返回信息
        return msg;
    }
    private String chooseWay2HandleGoods(GoodsResource goodsResource, List<GoodsBag> goodsBagList, ConcreteRole role, String goodsName) {
        //goodsResource为空，就添加；不为空，就更新
        if(goodsResource!=null){
            //获取物品数量
            String count = getCount(goodsResource,goodsBagList);
            //物品数量+1
            Integer newCount = Integer.parseInt(count)+1;
            //获取goodsBag
            addGoodsBag2List(goodsResource,goodsBagList,newCount);
            //存储缓存
            LocalGoodsManager.getLocalGoodsMap().put(role.getId(),goodsBagList);
        }else{
            //GoodsResource
            GoodsResource resource = CacheUtils.getGoodsMap().get(goodsName);
            //GoodsBag
            GoodsBag goodsBag = new GoodsBag();
            goodsBag.setCount(String.valueOf(1));
            goodsBag.setGoodsId(String.valueOf(resource.getId()));
            if(goodsBagList==null){
                goodsBagList = new ArrayList<>();
            }
            goodsBagList.add(goodsBag);
            //save
            //存储缓存
            LocalGoodsManager.getLocalGoodsMap().put(role.getId(),goodsBagList);
        }
        return role.getName()+"获得物品："+goodsName;
    }

    /**
     * 修改GoodsBag
     * @param goodsResource
     * @param goodsBagList
     * @param newCount
     */
    private void addGoodsBag2List(GoodsResource goodsResource, List<GoodsBag> goodsBagList,Integer newCount) {
        for (int i = 0; i < goodsBagList.size(); i++) {
            GoodsBag goodsBag = goodsBagList.get(i);
            if(goodsBag.getGoodsId().equals(goodsResource.getId())){
                goodsBag.setCount(String.valueOf(newCount));
                break;
            }
        }
    }

    /**
     * 获取goodsBag
     * @param goodsResource
     * @param goodsBagList
     * @return
     */
    private GoodsBag getGoodsBag(GoodsResource goodsResource, List<GoodsBag> goodsBagList) {
        GoodsBag goodsBag2 = null;

        return goodsBag2;
    }

    /**
     * 获取物品数量
     * @param goodsResource
     * @param goodsBagList
     * @return
     */
    private String getCount(GoodsResource goodsResource, List<GoodsBag> goodsBagList) {
        String count = null;
        for (int i = 0; i < goodsBagList.size(); i++) {
            GoodsBag goodsBag = goodsBagList.get(i);
            if(goodsBag.getGoodsId().equals(goodsResource.getId())){
                count = goodsBag.getCount();
                break;
            }
        }
        return count;
    }

    /**
     * 选择增加或更新物品方式
     * @param list 角色已拥有的物品列表
     * @param goods 物品
     * @param goodsName 物品名
     * @param role 角色
     * @return 字符串
     */
    private String chooseWay(List<GoodsResource> list, GoodsResource goods, String goodsName, ConcreteRole role) {
        //角色拥有物品的数量
        int existedGoods = list.size();
        //不存在或物品数量已满且背包未满，就增加
        boolean flag1  = (goods==null||goods.getCount()==goods.getRepeat())&&(existedGoods<role.getBackpackCapacity());
        //存在且物品数量未满
        boolean flag2 = goods!=null&&goods.getCount()<goods.getRepeat();

        if(flag1){
            //在本地缓存拿装备的详细信息,在数据库中没信息，依赖roleName在本地缓存中查询
            GoodsResource localGoods = CacheUtils.getGoodsMap().get(goodsName);
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
    private GoodsResource findGoods(List<GoodsResource> list, String goodsName) {
        GoodsResource goods = null;
        //遍历物品
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getName().equals(goodsName)){
                goods = list.get(i);
            }
        }
        return goods;
    }

    /**
     * 找物品
     * @param goodsBagList 列表
     * @param goodsName 物品名称
     * @return 物品
     */
    private GoodsResource findGoodsResource(List<GoodsBag> goodsBagList, String goodsName) {
        GoodsResource goods = CacheUtils.getGoodsMap().get(goodsName);
        GoodsResource goodsResource = null;
        if(goodsBagList!=null){
            //遍历
            for (int i = 0; i < goodsBagList.size(); i++) {
                GoodsBag goodsBag = goodsBagList.get(i);
                String goodsId = goodsBag.getGoodsId();
                if(goods.getId().equals(goodsId)){
                    goodsResource = goods;
                    break;
                }
            }
        }

        return goodsResource;
    }

    /**
     * 返回角色
     * @param roleName 角色名
     * @return 返回角色
     */
    private ConcreteRole getRole(String roleName) {
        return CacheUtils.getMapRoleNameRole().get(roleName);
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
        List<GoodsResource> list = backpackService.getGoodsByRoleId(role.getId());
        //找物品(数据库)
        GoodsResource goods_db =findGoods(list,goodsName);
        //找物品（本地缓存）
        GoodsResource goods_local = CacheUtils.getGoodsMap().get(goodsName);
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
    private String discardWay(GoodsResource goods, String goodsName, ConcreteRole role) {
        //物品数量-1
        backpackService.updateGoodsByRoleIdDel(role.getId(),goods.getId());
        return role.getName()+"的"+goodsName+"数量-1";
    }
}
