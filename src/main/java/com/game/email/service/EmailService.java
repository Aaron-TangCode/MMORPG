package com.game.email.service;

import com.game.backpack.handler.BackpackHandler;
import com.game.protobuf.protoc.MsgEmailInfoProto;
import com.game.role.bean.ConcreteRole;
import com.game.utils.MapUtils;
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
        Map<String, ConcreteRole> roleMap = MapUtils.getMapRolename_Role();
        Set<Map.Entry<String, ConcreteRole>> entrySet = roleMap.entrySet();
        Iterator<Map.Entry<String, ConcreteRole>> iterator = entrySet.iterator();
        //发送物品
        sendGoods(iterator,goodsName);
        return null;
    }

    /**
     * 发送物品
     * @param iterator 迭代器
     * @param goodsName 物品名称
     */
    private void sendGoods(Iterator<Map.Entry<String, ConcreteRole>> iterator,String goodsName) {
        while (iterator.hasNext()) {
            Map.Entry<String, ConcreteRole> next = iterator.next();
            ConcreteRole role = next.getValue();
            Channel channel = role.getChannel();
            backpackHandler.getGoods(role.getName(),goodsName);
            String msg = "[系统]"+role.getName()+"获得："+goodsName;
            channel.writeAndFlush(msg);
        }
    }
}
