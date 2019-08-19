package com.game.map.handler;

import com.game.map.service.MapService;
import com.game.protobuf.protoc.MsgMapInfoProto;
import com.game.utils.ProtobufUtils;
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
 * @Description 地图处理器
 * @Author DELL
 * @Date 2019/8/9 15:11
 * @Version 1.0
 */
@Component
@ChannelHandler.Sharable
public class MapHandler extends SimpleChannelInboundHandler<MsgMapInfoProto.RequestMapInfo> {
    /**
     * 地图服务
     */
    @Autowired
    private MapService mapService;
    /**
     * 协议
     */
    private MsgMapInfoProto.ResponseMapInfo responseMapInfo;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgMapInfoProto.RequestMapInfo requestMapInfo) throws Exception {
        //获取channel
        Channel channel = ctx.channel();
        //协议号
        int typeNum = requestMapInfo.getType().getNumber();
        //分发任务
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
        ProtobufUtils.sendProtobufMessage(ctx,responseMapInfo);
//        ctx.writeAndFlush(responseMapInfo);
    }
}
