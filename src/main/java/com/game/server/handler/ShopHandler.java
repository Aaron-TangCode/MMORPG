package com.game.server.handler;

import com.game.protobuf.protoc.MsgShopInfoProto;
import com.game.shop.ShopService.ShopService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.game.server.request.RequestShopInfoType.BUY;

/**
 * @ClassName ShopHandler
 * @Description TODO
 * @Author DELL
 * @Date 2019/8/12 18:18
 * @Version 1.0
 */
@Component
@ChannelHandler.Sharable
public class ShopHandler extends SimpleChannelInboundHandler<MsgShopInfoProto.RequestShopInfo> {
    @Autowired
    private ShopService shopService;

    private MsgShopInfoProto.ResponseShopInfo responseShopInfo;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgShopInfoProto.RequestShopInfo requestShopInfo) throws Exception {
        int typeNum = requestShopInfo.getType().getNumber();

        Channel channel = ctx.channel();

        switch (typeNum) {
            case BUY :
                responseShopInfo = shopService.buy(channel,requestShopInfo);
                break;
                default:
                    break;
        }
        ctx.writeAndFlush(responseShopInfo);
    }
}
