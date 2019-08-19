package com.game.role.handler;

import com.game.protobuf.protoc.MsgRoleInfoProto;
import com.game.role.service.RoleService;
import com.game.utils.ProtobufUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.game.server.request.RequestRoleInfoType.*;

/**
 * @ClassName RoleHandler
 * @Description 角色处理器
 * @Author DELL
 * @Date 2019/8/8 20:49
 * @Version 1.0
 */
@Component
@ChannelHandler.Sharable
public class RoleHandler extends SimpleChannelInboundHandler<MsgRoleInfoProto.RequestRoleInfo> {
    /**
     * 角色服务
     */
    @Autowired
    private RoleService roleService;
    /**
     * 协议信息
     */
    private MsgRoleInfoProto.ResponseRoleInfo responseRoleInfo;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgRoleInfoProto.RequestRoleInfo requestRoleInfo) throws Exception {
        Channel channel = ctx.channel();
        //协议号
        int requestType = requestRoleInfo.getType().getNumber();
        //分发任务
        switch (requestType) {
            case CHOOSEROLE :
                responseRoleInfo = roleService.chooseRole(channel,requestRoleInfo);
                break;
            case ROLEINFO :
                responseRoleInfo = roleService.roleInfo(channel,requestRoleInfo);
                break;
            case USEGOODS :
                responseRoleInfo = roleService.useGoods(channel,requestRoleInfo);
                break;
                default:
                    break;
        }
        //返回消息
        ProtobufUtils.sendProtobufMessage(ctx,responseRoleInfo);
    }
}
