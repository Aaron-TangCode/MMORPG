package com.game.rank.handler;

import com.game.protobuf.protoc.MsgRankInfoProto;
import com.game.rank.service.RankService;
import com.game.utils.ProtobufUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.game.server.request.RequestRankInfoType.*;

/**
 * @ClassName RankHandler
 * @Description 排行榜处理器
 * @Author DELL
 * @Date 2019/8/13 20:10
 * @Version 1.0
 */
@Component
@ChannelHandler.Sharable
public class RankHandler extends SimpleChannelInboundHandler<MsgRankInfoProto.RequestRankInfo> {
    /**
     * 排行榜服务
     */
    @Autowired
    private RankService rankService;
    /**
     * 协议
     */
    private MsgRankInfoProto.ResponseRankInfo responseRankInfo;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgRankInfoProto.RequestRankInfo requestRankInfo) throws Exception {
        //channel
        Channel channel = ctx.channel();
        //协议号
        int typeNum = requestRankInfo.getType().getNumber();
        //分发任务
        switch (typeNum) {
            case QUERYRANK :
                responseRankInfo = rankService.queryRankInfo(channel,requestRankInfo);
                break;
            case INSERTRANK:
                responseRankInfo = rankService.insertRankInfo(channel,requestRankInfo);
                break;
            case UPDATERANK:
                responseRankInfo = rankService.updateRankInfo(channel,requestRankInfo);
                break;
            case DELETERANK :
                responseRankInfo = rankService.deleteRankInfo(channel,requestRankInfo);
                break;
                default:
                    break;
        }
        //发送消息
        ProtobufUtils.sendProtobufMessage(ctx,responseRankInfo);
    }
}
