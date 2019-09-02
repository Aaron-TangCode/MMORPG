package com.game.email.service;

import com.game.backpack.handler.BackpackHandler;
import com.game.protobuf.protoc.MsgEmailInfoProto;
import com.game.role.bean.ConcreteRole;
import com.game.utils.CacheUtils;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName EmailService
 * @Description 邮件服务
 * @Author DELL
 * @Date 2019/8/13 11:12
 * @Version 1.0
 */
@Service
public class EmailService {
    /**
     * 背包控制器
     */
    @Autowired
    private BackpackHandler backpackHandler;

    /**
     * 发送物品
     * @param channel channel
     * @param requestEmailInfo 邮件请求信息
     * @return
     */
    public MsgEmailInfoProto.ResponseEmailInfo sendGoods(Channel channel, MsgEmailInfoProto.RequestEmailInfo requestEmailInfo) {
        //goodsName
        String goodsName = requestEmailInfo.getGoodsName();
        //获取所有玩家
        Map<String, ConcreteRole> roleMap = CacheUtils.getMapRoleNameRole();
        Set<Map.Entry<String, ConcreteRole>> entrySet = roleMap.entrySet();
        Iterator<Map.Entry<String, ConcreteRole>> iterator = entrySet.iterator();
        //发送物品
        sendGoods(iterator,goodsName);
        //content
        String cotent = "系统发送一个物品："+goodsName;
        //返回协议信息
        return MsgEmailInfoProto.ResponseEmailInfo.newBuilder()
                .setContent(cotent)
                .setType(MsgEmailInfoProto.RequestType.SENDGOODS)
                .build();
    }

    /**
     * 发送物品
     * @param iterator 迭代器
     * @param goodsName 物品名称
     */
    private void sendGoods(Iterator<Map.Entry<String, ConcreteRole>> iterator,String goodsName) {
        while (iterator.hasNext()) {
            //迭代器
            Map.Entry<String, ConcreteRole> next = iterator.next();
            //获取role
            ConcreteRole role = next.getValue();
            //获取channel
            Channel channel = role.getChannel();
            //获取物品
            backpackHandler.getGoods(role.getName(),goodsName);
            //content
            String msg = "[系统]"+role.getName()+"获得："+goodsName;
            //返回消息
            channel.writeAndFlush(msg);
        }
    }
}
