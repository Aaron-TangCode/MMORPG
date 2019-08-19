package com.game.user.handler;

import com.game.protobuf.protoc.MsgUserInfoProto;
import com.game.user.service.UserService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.game.server.request.RequestUserInfoType.*;

/**
 * @ClassName UserInfoHandler
 * @Description TODO
 * @Author DELL
 * @Date 2019/8/7 18:09
 * @Version 1.0
 */
@Component
@ChannelHandler.Sharable
public class UserInfoHandler extends SimpleChannelInboundHandler<MsgUserInfoProto.RequestUserInfo> {
    @Autowired
    private UserService userService;


    private MsgUserInfoProto.ResponseUserInfo responseUserInfo;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgUserInfoProto.RequestUserInfo requestUserInfo) throws Exception {
        Channel channel = ctx.channel();

        int requestType = requestUserInfo.getType().getNumber();

        switch (requestType){
            case LOGIN:
                responseUserInfo = userService.login(channel, requestUserInfo);
                break;
            case REGISTER:
                responseUserInfo = userService.register(channel, requestUserInfo);
                break;
            case EXIT:
                responseUserInfo = userService.exit(channel, requestUserInfo);
                break;
                default:
                    break;
        }

        // 返回消息
        ctx.writeAndFlush(responseUserInfo);
    }
}
