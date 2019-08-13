package com.game.email.service;

import com.game.backpack.controller.BackpackController;
import com.game.protobuf.protoc.MsgEmailInfoProto;
import com.game.role.bean.ConcreteRole;
import com.game.utils.MapUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName EmailService
 * @Description TODO
 * @Author DELL
 * @Date 2019/8/13 11:12
 * @Version 1.0
 */
@Service
public class EmailService {
    @Autowired
    private BackpackController backpackController;
    public MsgEmailInfoProto.ResponseEmailInfo sendGoods(Channel channel, MsgEmailInfoProto.RequestEmailInfo requestEmailInfo) {
        //goodsName
        String goodsName = requestEmailInfo.getGoodsName();
        //获取所有玩家
        Map<String, ConcreteRole> roleMap = MapUtils.getMapRolename_Role();
        Set<Map.Entry<String, ConcreteRole>> entrySet = roleMap.entrySet();
        Iterator<Map.Entry<String, ConcreteRole>> iterator = entrySet.iterator();
        sendGoods(iterator,goodsName);
        return null;
    }
    private void sendGoods(Iterator<Map.Entry<String, ConcreteRole>> iterator,String goodsName) {
        while (iterator.hasNext()) {
            Map.Entry<String, ConcreteRole> next = iterator.next();
            ConcreteRole role = next.getValue();
            ChannelHandlerContext ctx = role.getCtx();
            backpackController.getGoods(role.getName(),goodsName);
            String msg = "[系统]"+role.getName()+"获得："+goodsName;
            ctx.channel().writeAndFlush(msg);
        }
    }
}
