package com.game.chat.service;

import com.game.protobuf.message.ContentType;
import com.game.protobuf.protoc.MsgChatInfoProto;
import com.game.role.bean.ConcreteRole;
import com.game.user.manager.LocalUserMap;
import com.game.utils.MapUtils;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName ChatService
 * @Description TODO
 * @Author DELL
 * @Date 2019/8/12 21:34
 * @Version 1.0
 */
@Service
public class ChatService {
    /**
     * 世界聊天
     * @param channel
     * @param requestChatInfo
     * @return
     */
    public MsgChatInfoProto.ResponseChatInfo chatAll(Channel channel, MsgChatInfoProto.RequestChatInfo requestChatInfo) {
        //获取角色map
        Map<String, ConcreteRole> roleMap = MapUtils.getMapRolename_Role();
        Set<Map.Entry<String, ConcreteRole>> entrySet = roleMap.entrySet();
        Iterator<Map.Entry<String, ConcreteRole>> iterator = entrySet.iterator();
        //role
        ConcreteRole role = getRole(channel);
        //content
        String content = requestChatInfo.getContent();
        sendMessage(iterator,role.getName(),content);
        return MsgChatInfoProto.ResponseChatInfo.newBuilder()
                .setContent(ContentType.SEND_SUCCESS)
                .setType(MsgChatInfoProto.RequestType.CHATALL)
                .build();
    }
    private void sendMessage(Iterator<Map.Entry<String, ConcreteRole>> iterator,String roleName,String content) {
        while (iterator.hasNext()) {
            Map.Entry<String, ConcreteRole> next = iterator.next();
            ConcreteRole targetRole = next.getValue();

            //channel
            Channel channel = targetRole.getChannel();
            String msg = "[世界]"+roleName +":"+content;
            MsgChatInfoProto.ResponseChatInfo responseChatInfo = MsgChatInfoProto.ResponseChatInfo.newBuilder()
                    .setContent(msg)
                    .setType(MsgChatInfoProto.RequestType.CHATALL)
                    .build();
            channel.writeAndFlush(responseChatInfo);
        }
    }

    public ConcreteRole getRole(Channel channel){
        Integer userId = LocalUserMap.getChannelUserMap().get(channel);

        ConcreteRole role = LocalUserMap.getUserRoleMap().get(userId);
        return role;
    }
    /**
     * 私聊
     * @param channel
     * @param requestChatInfo
     * @return
     */
    public MsgChatInfoProto.ResponseChatInfo chatSomeone(Channel channel, MsgChatInfoProto.RequestChatInfo requestChatInfo) {
        //role
        ConcreteRole tmpRole = getRole(channel);
        //content
        String content = requestChatInfo.getContent();
        //target
        String target = requestChatInfo.getTarget();

        Map<String, ConcreteRole> roleMap = MapUtils.getMapRolename_Role();
        ConcreteRole role = roleMap.get(target);
        Channel channel1 = role.getChannel();
        String msg = "[私聊]"+tmpRole.getName()+":"+content;
        MsgChatInfoProto.ResponseChatInfo responseChatInfo = MsgChatInfoProto.ResponseChatInfo.newBuilder()
                .setContent(msg)
                .setType(MsgChatInfoProto.RequestType.CHATSOMEONE)
                .build();
        channel1.writeAndFlush(responseChatInfo);
        return MsgChatInfoProto.ResponseChatInfo.newBuilder()
                .setContent(ContentType.SEND_SUCCESS)
                .setType(MsgChatInfoProto.RequestType.CHATSOMEONE)
                .build();
    }
}
