package com.game.npc.handler;

import com.game.npc.service.NpcService;
import com.game.protobuf.protoc.MsgNpcInfoProto;
import com.game.utils.ProtobufUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.game.server.request.RequestNpcInfoType.TALKTONPC;

/**
 * @ClassName NpcHandler
 * @Description npc处理器
 * @Author DELL
 * @Date 2019/8/12 10:20
 * @Version 1.0
 */
@Component
@ChannelHandler.Sharable
public class NpcHandler extends SimpleChannelInboundHandler<MsgNpcInfoProto.RequestNpcInfo> {
    /**
     * npc服务
     */
    @Autowired
    private NpcService npcService;
    /**
     * 协议信息
     */
    private MsgNpcInfoProto.ResponseNpcInfo responseNpcInfo;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgNpcInfoProto.RequestNpcInfo requestNpcInfo) throws Exception {
        Channel channel = ctx.channel();
        //协议号
        int typeNum = requestNpcInfo.getType().getNumber();
        //分发任务
        switch (typeNum) {
            case TALKTONPC :
                responseNpcInfo = npcService.talkToNpc(channel,requestNpcInfo);
                break;
                default:
                    break;
        }
        //返回消息
        ProtobufUtils.sendProtobufMessage(ctx,responseNpcInfo);
    }
}
