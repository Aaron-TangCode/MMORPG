package com.game.duplicate.handler;

import com.game.duplicate.serivce.DuplicateService;
import com.game.protobuf.protoc.MsgBossInfoProto;
import com.game.utils.ProtobufUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.game.server.request.RequestBossInfoType.ATTACKBOSS;

/**
 * @ClassName DuplicateHandler
 * @Description 副本处理器
 * @Author DELL
 * @Date 2019/8/12 17:34
 * @Version 1.0
 */
@Component
@ChannelHandler.Sharable
public class DuplicateHandler extends SimpleChannelInboundHandler<MsgBossInfoProto.RequestBossInfo> {
    /**
     * 副本服务
     */
    @Autowired
    private DuplicateService duplicateService;
    /**
     * 协议
     */
    private MsgBossInfoProto.ResponseBossInfo responseBossInfo;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgBossInfoProto.RequestBossInfo requestBossInfo) throws Exception {
        Channel channel = ctx.channel();
        //协议号
        int typeNum = requestBossInfo.getType().getNumber();
        //分发任务
        switch (typeNum) {
            case ATTACKBOSS :
                responseBossInfo = duplicateService.attackBoss(channel,requestBossInfo);
                break;
                default:
                    break;
        }
        ProtobufUtils.sendProtobufMessage(ctx,responseBossInfo);
//        ctx.writeAndFlush(responseBossInfo);
    }
}
