package com.game.server.handler;

import com.game.protobuf.protoc.MsgRoleInfoProto;
import com.game.role.service.RoleService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.game.server.request.RequestRoleInfoType.CHOOSEROLE;
import static com.game.server.request.RequestRoleInfoType.ROLEINFO;

/**
 * @ClassName RoleHandler
 * @Description TODO
 * @Author DELL
 * @Date 2019/8/8 20:49
 * @Version 1.0
 */
@Component
@ChannelHandler.Sharable
public class RoleHandler extends SimpleChannelInboundHandler<MsgRoleInfoProto.RequestRoleInfo> {
    @Autowired
    private RoleService roleService;
    private MsgRoleInfoProto.ResponseRoleInfo responseRoleInfo;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgRoleInfoProto.RequestRoleInfo requestRoleInfo) throws Exception {
        Channel channel = ctx.channel();

        int requestType = requestRoleInfo.getType().getNumber();

        switch (requestType) {
            case CHOOSEROLE :
                responseRoleInfo = roleService.chooseRole(channel,requestRoleInfo);
                break;
            case ROLEINFO :
                responseRoleInfo = roleService.roleInfo(channel,requestRoleInfo);
                break;
                default:
                    break;
        }
        //返回消息
        channel.writeAndFlush(responseRoleInfo);
    }
}
