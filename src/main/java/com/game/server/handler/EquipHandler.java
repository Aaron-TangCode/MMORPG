package com.game.server.handler;

import com.game.equipment.service.EquipmentService;
import com.game.protobuf.protoc.MsgEquipInfoProto;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.game.server.request.RequestEquipInfoType.ADDEQUIP;
import static com.game.server.request.RequestEquipInfoType.REMOVEEQUIP;

/**
 * @ClassName EquipHandler
 * @Description TODO
 * @Author DELL
 * @Date 2019/8/12 16:45
 * @Version 1.0
 */
@Component
@ChannelHandler.Sharable
public class EquipHandler extends SimpleChannelInboundHandler<MsgEquipInfoProto.RequestEquipInfo> {
    @Autowired
    private EquipmentService equipmentService;

    private MsgEquipInfoProto.ResponseEquipInfo responseEquipInfo;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgEquipInfoProto.RequestEquipInfo requestEquipInfo) throws Exception {
        Channel channel = ctx.channel();
        int typeNum = requestEquipInfo.getType().getNumber();

        switch (typeNum) {
            case ADDEQUIP :
                responseEquipInfo = equipmentService.addEquip(channel,requestEquipInfo);
                break;
            case REMOVEEQUIP :
                responseEquipInfo = equipmentService.removeEquip(channel,requestEquipInfo);
                break;
                default:
                    break;
        }
        ctx.writeAndFlush(responseEquipInfo);
    }
}
