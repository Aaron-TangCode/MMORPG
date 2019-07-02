package com.game.email;

import com.game.backpack.controller.BackpackController;
import com.game.dispatcher.RequestAnnotation;
import com.game.role.bean.ConcreteRole;
import com.game.utils.MapUtils;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName EmailController
 * @Description TODO
 * @Author DELL
 * @Date 2019/7/2 15:29
 * @Version 1.0
 */
@RequestAnnotation("/email")
public class EmailController {

    @Autowired
    private BackpackController backpackController;

    /**
     * 发送道具
     * @param goodsName
     * @return
     */
    @RequestAnnotation("/goods")
    public void send(String goodsName){
        //获取所有玩家
        Map<String, ConcreteRole> roleMap = MapUtils.getMapRolename_Role();
        Set<Map.Entry<String, ConcreteRole>> entrySet = roleMap.entrySet();
        Iterator<Map.Entry<String, ConcreteRole>> iterator = entrySet.iterator();
        sendGoods(iterator,goodsName);

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
