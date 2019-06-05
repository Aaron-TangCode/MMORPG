package com.hailintang.socket_server_client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

/**
 * @ClassName MyClientHandler
 * @Description 客户端处理器
 * @Author DELL
 * @Date 2019/5/2220:11
 * @Version 1.0
 */
public class MyClientHandler extends SimpleChannelInboundHandler<String> {
    /**
     * 处理服务端请求
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress());
        System.out.println("client output:"+msg);
        ctx.channel().writeAndFlush("from client:"+ LocalDateTime.now());

    }

    /**
     * 捕捉异常
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 建立连接后,channel被激活
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().writeAndFlush("来自客户端的问候！！！");
    }
}
