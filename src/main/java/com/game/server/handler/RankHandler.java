package com.game.server.handler;

import com.game.protobuf.protoc.MsgRankInfoProto;
import com.game.rank.service.RankService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.game.server.request.RequestRankInfoType.*;

/**
 * @ClassName RankHandler
 * @Description TODO
 * @Author DELL
 * @Date 2019/8/13 20:10
 * @Version 1.0
 */
@Component
@ChannelHandler.Sharable
public class RankHandler extends SimpleChannelInboundHandler<MsgRankInfoProto.RequestRankInfo> {
    @Autowired
    private RankService rankService;

    private MsgRankInfoProto.ResponseRankInfo responseRankInfo;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgRankInfoProto.RequestRankInfo requestRankInfo) throws Exception {
        Channel channel = ctx.channel();

        int typeNum = requestRankInfo.getType().getNumber();

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
        ctx.writeAndFlush(responseRankInfo);
    }
}
