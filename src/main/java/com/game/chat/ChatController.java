package com.game.chat;

import com.game.dispatcher.RequestAnnotation;
import com.game.role.bean.ConcreteRole;
import com.game.utils.MapUtils;
import io.netty.channel.Channel;

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
            //channel
            Channel channel = targetRole.getChannel();
            String msg = "[世界]"+roleName +":"+content;
            channel.writeAndFlush(msg);
        }
    }

    @RequestAnnotation("/someone")
    public void chatToSomeone(String roleName,String roleTarget,String  content){
        Map<String, ConcreteRole> roleMap = MapUtils.getMapRolename_Role();
        ConcreteRole role = roleMap.get(roleTarget);
        //channel
        Channel channel = role.getChannel();
        String msg = "[私聊]"+roleName+":"+content;
        channel.writeAndFlush(msg);
    }
}
