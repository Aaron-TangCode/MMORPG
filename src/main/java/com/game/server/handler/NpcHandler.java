package com.game.server.handler;

import com.game.npc.service.NpcService;
import com.game.protobuf.protoc.MsgNpcInfoProto;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.game.server.request.RequestNpcInfoType.TALKTONPC;

/**
 * @ClassName NpcHandler
 * @Description TODO
 * @Author DELL
 * @Date 2019/8/12 10:20
 * @Version 1.0
 */
@Component
@ChannelHandler.Sharable
public class NpcHandler extends SimpleChannelInboundHandler<MsgNpcInfoProto.RequestNpcInfo> {
    @Autowired
    private NpcService npcService;

    private MsgNpcInfoProto.ResponseNpcInfo responseNpcInfo;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgNpcInfoProto.RequestNpcInfo requestNpcInfo) throws Exception {
        Channel channel = ctx.channel();
        int typeNum = requestNpcInfo.getType().getNumber();

        switch (typeNum) {
            case TALKTONPC :
                responseNpcInfo = npcService.talkToNpc(channel,requestNpcInfo);
                break;
                default:
                    break;
        }
        //返回消息
        channel.writeAndFlush(responseNpcInfo);
    }
}
