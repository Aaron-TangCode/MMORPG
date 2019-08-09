package com.game.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName ServerIdleStateHandler
 * @Description TODO
 * @Author DELL
 * @Date 2019/8/8 11:33
 * @Version 1.0
 */
public class ServerIdleStateHandler extends IdleStateHandler {

    private static final int READER_IDLE_TIME = 60 * 5;

    public ServerIdleStateHandler(){
        super(READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("from server:成功连接服务端");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("out line");
    }
}
