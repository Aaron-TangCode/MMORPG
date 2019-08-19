package com.game.email.handler;

import com.game.email.service.EmailService;
import com.game.protobuf.protoc.MsgEmailInfoProto;
import com.game.utils.ProtobufUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.game.server.request.RequestEmailInfoType.SENDGOODS;

/**
 * @ClassName EmailHandler
 * @Description 邮件处理器
 * @Author DELL
 * @Date 2019/8/13 11:09
 * @Version 1.0
 */
@Component
@ChannelHandler.Sharable
public class EmailHandler extends SimpleChannelInboundHandler<MsgEmailInfoProto.RequestEmailInfo> {
    /**
     * 邮件服务
     */
    @Autowired
    private EmailService emailService;
    /**
     * 协议
     */
    private MsgEmailInfoProto.ResponseEmailInfo responseEmailInfo;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgEmailInfoProto.RequestEmailInfo requestEmailInfo) throws Exception {
        Channel channel = ctx.channel();
        //协议号
        int typeNum = requestEmailInfo.getType().getNumber();
        //任务分发
        switch (typeNum) {
            case SENDGOODS :
                responseEmailInfo = emailService.sendGoods(channel,requestEmailInfo);
                break;
                default:
                    break;
        }
        //发送消息
        ProtobufUtils.sendProtobufMessage(ctx,responseEmailInfo);
//        ctx.writeAndFlush(responseEmailInfo);
    }
}
