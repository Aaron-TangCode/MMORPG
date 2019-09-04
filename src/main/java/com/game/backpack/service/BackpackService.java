package com.game.backpack.service;

import com.game.backpack.bean.GoodsEntity;
import com.game.backpack.bean.GoodsResource;
import com.game.backpack.handler.BackpackHandler;
import com.game.backpack.repository.BackpackRepository;
import com.game.protobuf.protoc.MsgGoodsInfoProto;
import com.game.role.bean.ConcreteRole;
import com.game.role.service.RoleService;
import com.game.user.manager.LocalUserMap;
import com.game.utils.CacheUtils;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName BackpackService
 * @Description 背包服务
 * @Author DELL
 * @Date 2019/6/17 15:36
 * @Version 1.0
 */
@Service
public class BackpackService {
    /**
     * 角色服务
     */
    @Autowired
    private RoleService roleService;
    /**
     * 背包数据访问层
     */
    @Autowired
    private BackpackRepository backpackRepository;

    @Autowired
    private BackpackHandler backpackHandler;

    /**
     * 通过角色id获取物品
     * @param roleId 角色id
     * @return 返回物品列表
     */
    public List<GoodsResource> getGoodsByRoleId(int roleId) {
        return backpackRepository.getGoodsByRoleId(roleId);
    }

    /**
     * 插入物品
     * @param goods 物品
     */
    public void insertGoods(GoodsResource goods) {
        backpackRepository.insertGoods(goods);
    }

    /**
     * 通过角色id更新物品
     * @param roleId 角色id
     * @param goodsId 物品id
     */
    public void updateGoodsByRoleId(int roleId,int goodsId) {
        backpackRepository.updateGoodsByRoleId(roleId,goodsId);
    }

    /**
     * 更新物品(减)
     * @param roleId 角色id
     * @param goodsId 物品id
     */
    public void updateGoodsByRoleIdDel(int roleId, Integer goodsId) {
        backpackRepository.updateGoodsByRoleIdDel(roleId,goodsId);
    }

    /**
     * 获取物品id
     * @param goodsId 物品id
     * @return 返回物品
     */
    public GoodsResource getGoodsById(String goodsId) {
        return backpackRepository.getGoodsById(goodsId);
    }

    /**
     * 获取物品
     * @param channel Channel
     * @param requestGoodsInfo 物品请求信息
     * @return 物品协议信息
     */
    public MsgGoodsInfoProto.ResponseGoodsInfo getGoods(Channel channel, MsgGoodsInfoProto.RequestGoodsInfo requestGoodsInfo) {
        //获取userId
        Integer userId = LocalUserMap.getChannelUserMap().get(channel);
        //获取role
        ConcreteRole role = LocalUserMap.getUserRoleMap().get(userId);
        //获取物品名称
        String goodsName = requestGoodsInfo.getGoodsName();
        //逻辑处理
//        String content = handleGoods(goodsName,role);
        String content = backpackHandler.getGoods(role.getName(),goodsName);
        //返回消息
        return MsgGoodsInfoProto.ResponseGoodsInfo.newBuilder()
                .setType(MsgGoodsInfoProto.RequestType.GETGOODS)
                .setContent(content)
                .build();
    }

    /**
     * 获取物品
     * @param channel Channel
     * @param goodsName 物品名称
     * @return 协议信息
     */
    public void getGoods(Channel channel,String goodsName){
        //获取userId
        Integer userId = LocalUserMap.getChannelUserMap().get(channel);
        //获取role
        ConcreteRole role = LocalUserMap.getUserRoleMap().get(userId);
        //逻辑处理
        String content ="掉落物品："+goodsName+"\t"+handleGoods(goodsName,role);

        //返回消息
        MsgGoodsInfoProto.ResponseGoodsInfo info = MsgGoodsInfoProto.ResponseGoodsInfo.newBuilder()
                .setType(MsgGoodsInfoProto.RequestType.GETGOODS)
                .setContent(content)
                .build();
        channel.writeAndFlush(info);
    }

    /**
     * 处理物品
     * @param goodsName 物品名称
     * @param role 角色
     * @return 返回信息
     */
    private String handleGoods(String goodsName, ConcreteRole role) {
        //在数据库查询，根据角色id查询是否具有物品
        List<GoodsResource> list = getGoodsByRoleId(role.getId());
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
     * 丢物品
     * @param channel Channel
     * @param requestGoodsInfo 物品请求信息
     * @return 物品协议信息
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

    /**
     * 处理丢物品
     * @param role 角色
     * @param goodsName 物品名称
     * @return 消息
     */
    private String discardHandle(ConcreteRole role, String goodsName) {
        //在数据库查询，根据角色id查询是否具有物品
        List<GoodsResource> list = getGoodsByRoleId(role.getId());
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
     * 丢掉
     * @param goods 物品
     * @param goodsName 物品名称
     * @param role 角色
     * @return 消息
     */
    private String discardWay(GoodsResource goods, String goodsName, ConcreteRole role) {
        //物品数量-1
        updateGoodsByRoleIdDel(role.getId(),goods.getId());
        return role.getName()+"的"+goodsName+"数量-1";
    }

    /**
     * 显示物品信息
     * @param channel channel
     * @param requestGoodsInfo requestGoodsInfo
     * @return 协议信息
     */
    public MsgGoodsInfoProto.ResponseGoodsInfo showGoods(Channel channel, MsgGoodsInfoProto.RequestGoodsInfo requestGoodsInfo) {
        ConcreteRole role = getRole(channel);
        List<GoodsResource> goodsList = backpackRepository.getGoodsByRoleId(role.getId());
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < goodsList.size(); i++) {
            GoodsResource goods = goodsList.get(i);
            if(goods.getCount()<=0){
                continue;
            }
            content.append(goods.getName()+"\t").append(goods.getCount()+"\t").append("\n");
        }
        //moneyRole
        ConcreteRole moneyRole = roleService.getRole(role.getId());
        content.append("金币：\t"+moneyRole.getMoney());
        return MsgGoodsInfoProto.ResponseGoodsInfo.newBuilder()
                .setContent(content.toString())
                .setType(MsgGoodsInfoProto.RequestType.SHOWGOODS)
                .build();
    }

    /**
     * 获取角色
     * @param channel
     * @return
     */
    private ConcreteRole getRole(Channel channel) {
        Integer useId = LocalUserMap.getChannelUserMap().get(channel);
        ConcreteRole role = LocalUserMap.getUserRoleMap().get(useId);
        return role;
    }

    /**
     * 根据角色id获取物品
     * @param roleId 角色id
     * @return 物品
     */
    public GoodsEntity getGoodsEntityByRoleId(int roleId) {
        return backpackRepository.getGoodsEntityByRoleId(roleId);
    }

    /**
     * 保存物品信息
     */
    public void saveGoodsInfo(GoodsEntity goodsEntity) {
        backpackRepository.saveGoodsInfo(goodsEntity);
    }
}
