package com.hailintang.socket_chat_server_multiclient.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @ClassName MyChatClientHandler
 * @Description TODO
 * @Author DELL
 * @Date 2019/5/2311:28
 * @Version 1.0
 */
public class MyChatClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(msg);
    }
}
