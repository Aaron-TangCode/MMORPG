package com.game.server.handler;

import com.game.gang.service.GangService;
import com.game.protobuf.protoc.MsgGangInfoProto;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.game.server.request.RequestGangInfoType.*;

/**
 * @ClassName GangHandler
 * @Description TODO
 * @Author DELL
 * @Date 2019/8/13 14:40
 * @Version 1.0
 */
@Component
@ChannelHandler.Sharable
public class GangHandler extends SimpleChannelInboundHandler<MsgGangInfoProto.RequestGangInfo> {
    @Autowired
    private GangService gangService;

    private MsgGangInfoProto.ResponseGangInfo responseGangInfo;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgGangInfoProto.RequestGangInfo requestGangInfo) throws Exception {
        Channel channel = ctx.channel();

        int number = requestGangInfo.getType().getNumber();

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
        ctx.writeAndFlush(responseGangInfo);
    }
}
