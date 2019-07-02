package com.game.chat;

import com.game.dispatcher.RequestAnnotation;
import com.game.role.bean.ConcreteRole;
import com.game.utils.MapUtils;
import io.netty.channel.ChannelHandlerContext;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName ChatController
 * @Description TODO
 * @Author DELL
 * @Date 2019/7/2 12:07
 * @Version 1.0
 */
@RequestAnnotation("/chat")
public class ChatController {
    @RequestAnnotation("/all")
    public void chatAll(String roleName,String content){
        Map<String, ConcreteRole> roleMap = MapUtils.getMapRolename_Role();
        Set<Map.Entry<String, ConcreteRole>> entrySet = roleMap.entrySet();
        Iterator<Map.Entry<String, ConcreteRole>> iterator = entrySet.iterator();
        sendMessage(iterator,roleName,content);

    }
    private void sendMessage(Iterator<Map.Entry<String, ConcreteRole>> iterator,String roleName,String content) {
        while (iterator.hasNext()) {
            Map.Entry<String, ConcreteRole> next = iterator.next();
            ConcreteRole targetRole = next.getValue();
            ChannelHandlerContext ctx = targetRole.getCtx();
            String msg = "[世界]"+roleName +":"+content;
            ctx.channel().writeAndFlush(msg);
        }
    }

    @RequestAnnotation("/someone")
    public void chatToSomeone(String roleName,String roleTarget,String  content){
        Map<String, ConcreteRole> roleMap = MapUtils.getMapRolename_Role();
        ConcreteRole role = roleMap.get(roleTarget);
        ChannelHandlerContext ctx = role.getCtx();
        String msg = "[私聊]"+roleName+":"+content;
        ctx.channel().writeAndFlush(msg);
    }
}
