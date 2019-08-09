package com.game.server.handler;

import com.game.map.service.MapService;
import com.game.protobuf.protoc.MsgMapInfoProto;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.game.server.request.RequestMapInfoType.GETMAP;
import static com.game.server.request.RequestMapInfoType.MOVE;

/**
 * @ClassName MapHandler
 * @Description TODO
 * @Author DELL
 * @Date 2019/8/9 15:11
 * @Version 1.0
 */
@Component
@ChannelHandler.Sharable
public class MapHandler extends SimpleChannelInboundHandler<MsgMapInfoProto.RequestMapInfo> {
    @Autowired
    private MapService mapService;

    private MsgMapInfoProto.ResponseMapInfo responseMapInfo;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgMapInfoProto.RequestMapInfo requestMapInfo) throws Exception {
        Channel channel = ctx.channel();

        int typeNum = requestMapInfo.getType().getNumber();

        switch (typeNum) {
            case GETMAP :
                responseMapInfo = mapService.getMapInfo(channel,requestMapInfo);
                break;
            case MOVE :
                responseMapInfo = mapService.move(channel,requestMapInfo);
                break;
                default:
                    break;
        }

        //返回消息
        ctx.writeAndFlush(responseMapInfo);
    }
}
