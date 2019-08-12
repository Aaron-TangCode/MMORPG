package com.game.server.handler;

import com.game.duplicate.serivce.DuplicateService;
import com.game.protobuf.protoc.MsgBossInfoProto;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.game.server.request.RequestBossInfoType.ATTACKBOSS;

/**
 * @ClassName DuplicateHandler
 * @Description TODO
 * @Author DELL
 * @Date 2019/8/12 17:34
 * @Version 1.0
 */
@Component
@ChannelHandler.Sharable
public class DuplicateHandler extends SimpleChannelInboundHandler<MsgBossInfoProto.RequestBossInfo> {
    @Autowired
    private DuplicateService duplicateService;

    private MsgBossInfoProto.ResponseBossInfo responseBossInfo;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgBossInfoProto.RequestBossInfo requestBossInfo) throws Exception {
        Channel channel = ctx.channel();

        int typeNum = requestBossInfo.getType().getNumber();

        switch (typeNum) {
            case ATTACKBOSS :
                responseBossInfo = duplicateService.attackBoss(channel,requestBossInfo);
                break;
                default:
                    break;
        }
        ctx.writeAndFlush(responseBossInfo);
    }
}
