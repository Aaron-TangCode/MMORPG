package com.game.equipment.handler;

import com.game.equipment.service.EquipmentService;
import com.game.protobuf.protoc.MsgEquipInfoProto;
import com.game.utils.ProtobufUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.game.server.request.RequestEquipInfoType.*;

/**
 * @ClassName EquipHandler
 * @Description 装备处理器
 * @Author DELL
 * @Date 2019/8/12 16:45
 * @Version 1.0
 */
@Component
@ChannelHandler.Sharable
public class EquipHandler extends SimpleChannelInboundHandler<MsgEquipInfoProto.RequestEquipInfo> {
    /**
     * 装备服务
     */
    @Autowired
    private EquipmentService equipmentService;
    /**
     * 协议
     */
    private MsgEquipInfoProto.ResponseEquipInfo responseEquipInfo;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgEquipInfoProto.RequestEquipInfo requestEquipInfo) throws Exception {
        Channel channel = ctx.channel();
        //协议号
        int typeNum = requestEquipInfo.getType().getNumber();
        //分发任务
        switch (typeNum) {
            case ADDEQUIP :
                responseEquipInfo = equipmentService.addEquip(channel,requestEquipInfo);
                break;
            case REMOVEEQUIP :
                responseEquipInfo = equipmentService.removeEquip(channel,requestEquipInfo);
                break;
            case SHOWEQUIP :
                responseEquipInfo = equipmentService.showEquip(channel,requestEquipInfo);
                break;
                default:
                    break;
        }
        //发送消息
        ProtobufUtils.sendProtobufMessage(ctx,responseEquipInfo);
//        ctx.writeAndFlush(responseEquipInfo);
    }
}
