package com.game.chat.handler;

import com.game.chat.service.ChatService;
import com.game.protobuf.protoc.MsgChatInfoProto;
import com.game.utils.ProtobufUtils;
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
 * @Description 聊天处理器
 * @Author DELL
 * @Date 2019/8/12 21:30
 * @Version 1.0
 */
@Component
@ChannelHandler.Sharable
public class ChatHandler extends SimpleChannelInboundHandler<MsgChatInfoProto.RequestChatInfo> {
    /**
     * 聊天服务
     */
    @Autowired
    private ChatService chatService;
    /**
     * 聊天协议
     */
    private MsgChatInfoProto.ResponseChatInfo responseChatInfo;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgChatInfoProto.RequestChatInfo requestChatInfo) throws Exception {
        Channel channel = ctx.channel();
        //获取协议号
        int typeNum = requestChatInfo.getType().getNumber();
        //分发任务
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
        //发送消息
        ProtobufUtils.sendProtobufMessage(ctx,responseChatInfo);
//        ctx.writeAndFlush(responseChatInfo);
    }
}
