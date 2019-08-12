package com.game.backpack.service;

import com.game.backpack.bean.Goods;
import com.game.backpack.repository.BackpackRepository;
import com.game.protobuf.protoc.MsgGoodsInfoProto;
import com.game.role.bean.ConcreteRole;
import com.game.user.manager.LocalUserMap;
import com.game.utils.MapUtils;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName BackpackService
 * @Description 背包逻辑层
 * @Author DELL
 * @Date 2019/6/17 15:36
 * @Version 1.0
 */
@Service
public class BackpackService {
    @Autowired
    private BackpackRepository backpackRepository;

    public List<Goods> getGoodsByRoleId(int roleId) {
        return backpackRepository.getGoodsByRoleId(roleId);
    }

    public void insertGoods(Goods newGoods) {
        backpackRepository.insertGoods(newGoods);
    }

    public void updateGoodsByRoleId(int roleId,int goodsId) {
        backpackRepository.updateGoodsByRoleId(roleId,goodsId);
    }

    public void updateGoodsByRoleIdDel(int roleId, Integer goodsId) {
        backpackRepository.updateGoodsByRoleIdDel(roleId,goodsId);
    }

    public Goods getGoodsById(String goodsId) {
        return backpackRepository.getGoodsById(goodsId);
    }

    /**
     * 获取物品
     * @param channel
     * @param requestGoodsInfo
     * @return
     */
    public MsgGoodsInfoProto.ResponseGoodsInfo getGoods(Channel channel, MsgGoodsInfoProto.RequestGoodsInfo requestGoodsInfo) {
        //获取userId
        Integer userId = LocalUserMap.getChannelUserMap().get(channel);
        //获取role
        ConcreteRole role = LocalUserMap.getUserRoleMap().get(userId);
        //获取物品名称
        String goodsName = requestGoodsInfo.getGoodsName();
        //逻辑处理
        String content = handleGoods(goodsName,role);
        //返回消息
        return MsgGoodsInfoProto.ResponseGoodsInfo.newBuilder()
                .setType(MsgGoodsInfoProto.RequestType.GETGOODS)
                .setContent(content)
                .build();
    }

    private String handleGoods(String goodsName, ConcreteRole role) {
        //在数据库查询，根据角色id查询是否具有物品
        List<Goods> list = getGoodsByRoleId(role.getId());
        //找物品(数据库)
        Goods goods_db =findGoods(list,goodsName);
        //找物品（本地缓存）
        Goods goods_local = MapUtils.getGoodsMap().get(goodsName);
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
            Goods localGoods = MapUtils.getGoodsMap().get(goodsName);
            if(localGoods==null){
                return "装备不存在本地缓存";
            }
            localGoods.setRoleId(role.getId());
            insertGoods(localGoods);
            return role.getName()+"获得物品："+goodsName;
        }else if (flag2){
            //存在且物品数量未满，就更新数量
            updateGoodsByRoleId(role.getId(),goods.getId());
            return role.getName()+"获得物品："+goodsName;
        } else {
            //如果获得的物品在背包中不存在或叠加数已满，且没有剩余的格子则返回背包已满的提示
            return role.getName()+"的背包已满";
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
     * 丢物品
     * @param channel
     * @param requestGoodsInfo
     * @return
     */
    public MsgGoodsInfoProto.ResponseGoodsInfo discardGoods(Channel channel, MsgGoodsInfoProto.RequestGoodsInfo requestGoodsInfo) {
        //获取userId
        Integer userId = LocalUserMap.getChannelUserMap().get(channel);
        //获取role
        ConcreteRole role = LocalUserMap.getUserRoleMap().get(userId);
        //获取商品名称
        String goodsName = requestGoodsInfo.getGoodsName();
        String content = discardHandle(role,goodsName);
            return MsgGoodsInfoProto.ResponseGoodsInfo.newBuilder()
                    .setType(MsgGoodsInfoProto.RequestType.DISCARDGOODS)
                    .setContent(content)
                    .build();
    }

    private String discardHandle(ConcreteRole role, String goodsName) {
        //在数据库查询，根据角色id查询是否具有物品
        List<Goods> list = getGoodsByRoleId(role.getId());
        //找物品(数据库)
        Goods goods_db =findGoods(list,goodsName);
        //找物品（本地缓存）
        Goods goods_local = MapUtils.getGoodsMap().get(goodsName);
        //更新本地的count信息
        if(goods_db!=null){
            goods_db.setRepeat(goods_local.getRepeat());
        }
        //选择丢弃
        String msg = discardWay(goods_db,goodsName,role);
        //返回信息
        return msg;
    }

    private String discardWay(Goods goods, String goodsName, ConcreteRole role) {
        //物品数量-1
        updateGoodsByRoleIdDel(role.getId(),goods.getId());
        return role.getName()+"的"+goodsName+"数量-1";
    }
}
