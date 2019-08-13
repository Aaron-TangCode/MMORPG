package com.game.server.handler;

import com.game.email.service.EmailService;
import com.game.protobuf.protoc.MsgEmailInfoProto;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.game.server.request.RequestEmailInfoType.SENDGOODS;

/**
 * @ClassName EmailHandler
 * @Description TODO
 * @Author DELL
 * @Date 2019/8/13 11:09
 * @Version 1.0
 */
@Component
@ChannelHandler.Sharable
public class EmailHandler extends SimpleChannelInboundHandler<MsgEmailInfoProto.RequestEmailInfo> {
    @Autowired
    private EmailService emailService;

    private MsgEmailInfoProto.ResponseEmailInfo responseEmailInfo;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgEmailInfoProto.RequestEmailInfo requestEmailInfo) throws Exception {
        Channel channel = ctx.channel();
        int typeNum = requestEmailInfo.getType().getNumber();

        switch (typeNum) {
            case SENDGOODS :
                responseEmailInfo = emailService.sendGoods(channel,requestEmailInfo);
                break;
                default:
                    break;
        }

        ctx.writeAndFlush(responseEmailInfo);
    }
}
