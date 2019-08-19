package com.game.auction.handler;

import com.game.auction.service.AuctionService;
import com.game.protobuf.protoc.MsgAuctionInfoProto;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.game.server.request.RequestAuctionInfoType.*;

/**
 * @ClassName AuctionHandler
 * @Description 拍卖处理器
 * @Author DELL
 * @Date 2019/8/13 16:43
 * @Version 1.0
 */
@Component
@ChannelHandler.Sharable
public class AuctionHandler extends SimpleChannelInboundHandler<MsgAuctionInfoProto.RequestAuctionInfo> {
    /**
     * 拍卖服务
     */
    @Autowired
    private AuctionService auctionService;
    /**
     * protobuf协议
     */
    private MsgAuctionInfoProto.ResponseAuctionInfo responseAuctionInfo;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgAuctionInfoProto.RequestAuctionInfo requestAuctionInfo) throws Exception {
        Channel channel = ctx.channel();
        //获取协议号码
        int number = requestAuctionInfo.getType().getNumber();

        switch (number) {
            case BIDDING :
                responseAuctionInfo = auctionService.bidding(channel,requestAuctionInfo);
                break;
            case RECYCLE :
                responseAuctionInfo = auctionService.recycle(channel,requestAuctionInfo);
                break;
            case PUBLISH:
                responseAuctionInfo = auctionService.publish(channel,requestAuctionInfo);
                break;
            case QUERYAUCTION :
                responseAuctionInfo = auctionService.queryAuction(channel,requestAuctionInfo);
                break;
                default:
                    break;
        }
        //发送协议
        ctx.writeAndFlush(responseAuctionInfo);
    }
}
