package com.game.chat.controller;

import com.game.dispatcher.RequestAnnotation;
import com.game.role.bean.ConcreteRole;
import com.game.utils.MapUtils;
import io.netty.channel.Channel;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName ChatController
 * @Description 聊天控制器
 * @Author DELL
 * @Date 2019/7/2 12:07
 * @Version 1.0
 */
@RequestAnnotation("/chat")
public class ChatController {
    /**
     * 世界聊天
     * @param roleName 角色名
     * @param content 内容
     */
    @RequestAnnotation("/all")
    public void chatAll(String roleName,String content){
        //获取角色
        Map<String, ConcreteRole> roleMap = MapUtils.getMapRolename_Role();
        //获取角色map
        Set<Map.Entry<String, ConcreteRole>> entrySet = roleMap.entrySet();
        Iterator<Map.Entry<String, ConcreteRole>> iterator = entrySet.iterator();
        //发送消息
        sendMessage(iterator,roleName,content);

    }

    /**
     * 发送消息
     * @param iterator 迭代器
     * @param roleName 角色名
     * @param content 内容
     */
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

    /**
     * 私聊
     * @param roleName 角色名
     * @param roleTarget 目标对象
     * @param content 内容
     */
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
