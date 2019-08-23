package com.game.gang.handler;

import com.game.gang.service.GangService;
import com.game.protobuf.protoc.MsgGangInfoProto;
import com.game.utils.ProtobufUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.game.server.request.RequestGangInfoType.*;

/**
 * @ClassName GangHandler
 * @Description 工会处理器
 * @Author DELL
 * @Date 2019/8/13 14:40
 * @Version 1.0
 */
@Component
@ChannelHandler.Sharable
public class GangHandler extends SimpleChannelInboundHandler<MsgGangInfoProto.RequestGangInfo> {
    /**
     * 工会服务
     */
    @Autowired
    private GangService gangService;
    /**
     * 协议
     */
    private MsgGangInfoProto.ResponseGangInfo responseGangInfo;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgGangInfoProto.RequestGangInfo requestGangInfo) throws Exception {
        Channel channel = ctx.channel();
        //协议号
        int number = requestGangInfo.getType().getNumber();
        //分发任务
        switch (number) {
            case CREATEGANG :
                responseGangInfo = gangService.createGang(channel,requestGangInfo);
                break;
            case JOINGANG :
                responseGangInfo = gangService.joinGang(channel,requestGangInfo);
                break;
            case DISMISSGANG :
                responseGangInfo = gangService.dismissGang(channel,requestGangInfo);
                break;
            case DONATEMONEY :
                responseGangInfo = gangService.donateMoney(channel,requestGangInfo);
                break;
                default:
                    break;
        }
        //发送协议
        ProtobufUtils.sendProtobufMessage(ctx,responseGangInfo);
    }
}
