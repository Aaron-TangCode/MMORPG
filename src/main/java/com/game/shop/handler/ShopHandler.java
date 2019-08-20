package com.game.shop.handler;

import com.game.protobuf.protoc.MsgShopInfoProto;
import com.game.shop.ShopService.ShopService;
import com.game.utils.ProtobufUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.game.server.request.RequestShopInfoType.BUY;

/**
 * @ClassName ShopHandler
 * @Description 商店处理器
 * @Author DELL
 * @Date 2019/8/12 18:18
 * @Version 1.0
 */
@Component
@ChannelHandler.Sharable
public class ShopHandler extends SimpleChannelInboundHandler<MsgShopInfoProto.RequestShopInfo> {
    /**
     * 商店服务
     */
    @Autowired
    private ShopService shopService;
    /**
     * 协议信息
     */
    private MsgShopInfoProto.ResponseShopInfo responseShopInfo;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgShopInfoProto.RequestShopInfo requestShopInfo) throws Exception {
        //协议号
        int typeNum = requestShopInfo.getType().getNumber();
        //channel
        Channel channel = ctx.channel();
        //分发任务
        switch (typeNum) {
            case BUY :
                responseShopInfo = shopService.buy(channel,requestShopInfo);
                break;
                default:
                    break;
        }
        //发送协议
        ProtobufUtils.sendProtobufMessage(ctx,responseShopInfo);
    }
}
