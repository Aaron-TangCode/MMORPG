package com.game.trade.handler;

import com.game.protobuf.protoc.MsgTradeInfoProto;
import com.game.trade.service.TradeService;
import com.game.utils.ProtobufUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.game.server.request.RequestTradeInfoType.*;

/**
 * @ClassName TradeHandler
 * @Description 交易处理器
 * @Author DELL
 * @Date 2019/8/13 12:17
 * @Version 1.0
 */
@Component
@ChannelHandler.Sharable
public class TradeHandler extends SimpleChannelInboundHandler<MsgTradeInfoProto.RequestTradeInfo> {
    /**
     * 交易服务
     */
    @Autowired
    private TradeService tradeService;
    /**
     * 协议
     */
    private MsgTradeInfoProto.ResponseTradeInfo responseTradeInfo;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgTradeInfoProto.RequestTradeInfo requestTradeInfo) throws Exception {
        //channel
        Channel channel = ctx.channel();
            //协议号
        int typeNum = requestTradeInfo.getType().getNumber();
        //分发任务
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
        //发送协议
        ProtobufUtils.sendProtobufMessage(ctx,responseTradeInfo);
    }
}
