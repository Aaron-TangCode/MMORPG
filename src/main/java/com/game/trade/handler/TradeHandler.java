package com.game.trade.handler;

import com.game.protobuf.protoc.MsgTradeInfoProto;
import com.game.trade.service.TradeService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.game.server.request.RequestTradeInfoType.*;

/**
 * @ClassName TradeHandler
 * @Description TODO
 * @Author DELL
 * @Date 2019/8/13 12:17
 * @Version 1.0
 */
@Component
@ChannelHandler.Sharable
public class TradeHandler extends SimpleChannelInboundHandler<MsgTradeInfoProto.RequestTradeInfo> {
    @Autowired
    private TradeService tradeService;

    private MsgTradeInfoProto.ResponseTradeInfo responseTradeInfo;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgTradeInfoProto.RequestTradeInfo requestTradeInfo) throws Exception {
        Channel channel = ctx.channel();

        int typeNum = requestTradeInfo.getType().getNumber();

        switch (typeNum) {
            case REQUESTTRADE :
                responseTradeInfo = tradeService.requestTrade(channel,requestTradeInfo);
                break;
            case CONFIRMTRADE :
                responseTradeInfo = tradeService.confirmTrade(channel,requestTradeInfo);
                break;
            case TRADINGGOODS :
                responseTradeInfo = tradeService.tradingGoods(channel,requestTradeInfo);
                break;
            case TRADINGMONEY :
                responseTradeInfo = tradeService.tradingMoney(channel,requestTradeInfo);
                break;
            case TRADEGOODS :
                responseTradeInfo = tradeService.tradeGoods(channel,requestTradeInfo);
                break;
                default:
                    break;
        }
        ctx.writeAndFlush(responseTradeInfo);
    }
}
