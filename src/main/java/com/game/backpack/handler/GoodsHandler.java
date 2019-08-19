package com.game.backpack.handler;

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
 * @Description 物品处理器
 * @Author DELL
 * @Date 2019/8/12 12:15
 * @Version 1.0
 */
@Component
@ChannelHandler.Sharable
public class GoodsHandler extends SimpleChannelInboundHandler<MsgGoodsInfoProto.RequestGoodsInfo> {
    /**
     * 背包服务
     */
    @Autowired
    private BackpackService backpackService;
    /**
     * 协议
     */
    private MsgGoodsInfoProto.ResponseGoodsInfo responseGoodsInfo;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgGoodsInfoProto.RequestGoodsInfo requestGoodsInfo) throws Exception {
        Channel channel = ctx.channel();
        //类型号码
        int typeNum = requestGoodsInfo.getType().getNumber();
        //分发服务
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
