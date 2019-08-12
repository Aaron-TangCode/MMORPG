package com.game.server.handler;

import com.game.backpack.service.BackpackService;
import com.game.protobuf.protoc.MsgGoodsInfoProto;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.game.server.request.RequestGoodsInfoType.DISCARDGOODS;
import static com.game.server.request.RequestGoodsInfoType.GETGOODS;

/**
 * @ClassName GoodsHandler
 * @Description TODO
 * @Author DELL
 * @Date 2019/8/12 12:15
 * @Version 1.0
 */
@Component
@ChannelHandler.Sharable
public class GoodsHandler extends SimpleChannelInboundHandler<MsgGoodsInfoProto.RequestGoodsInfo> {
    @Autowired
    private BackpackService backpackService;
    private MsgGoodsInfoProto.ResponseGoodsInfo responseGoodsInfo;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgGoodsInfoProto.RequestGoodsInfo requestGoodsInfo) throws Exception {
        Channel channel = ctx.channel();
        int typeNum = requestGoodsInfo.getType().getNumber();

        switch (typeNum) {
            case GETGOODS :
                responseGoodsInfo = backpackService.getGoods(channel,requestGoodsInfo);
                break;
            case DISCARDGOODS :
                responseGoodsInfo = backpackService.discardGoods(channel,requestGoodsInfo);
                break;
                default:
                    break;

        }
        //返回消息
        ctx.writeAndFlush(responseGoodsInfo);
    }
}
