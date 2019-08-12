package com.game.server.handler;

import com.game.chat.service.ChatService;
import com.game.protobuf.protoc.MsgChatInfoProto;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.game.server.request.RequestChatInfoType.CHATALL;
import static com.game.server.request.RequestChatInfoType.CHATSOMEONE;

/**
 * @ClassName ChatHandler
 * @Description TODO
 * @Author DELL
 * @Date 2019/8/12 21:30
 * @Version 1.0
 */
@Component
@ChannelHandler.Sharable
public class ChatHandler extends SimpleChannelInboundHandler<MsgChatInfoProto.RequestChatInfo> {
    @Autowired
    private ChatService chatService;

    private MsgChatInfoProto.ResponseChatInfo responseChatInfo;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgChatInfoProto.RequestChatInfo requestChatInfo) throws Exception {
        Channel channel = ctx.channel();

        int typeNum = requestChatInfo.getType().getNumber();

        switch (typeNum) {
            case CHATALL :
                responseChatInfo = chatService.chatAll(channel,requestChatInfo);
                break;
            case CHATSOMEONE :
                responseChatInfo = chatService.chatSomeone(channel,requestChatInfo);
                break;
                default:
                    break;
        }
        ctx.writeAndFlush(responseChatInfo);
    }
}
