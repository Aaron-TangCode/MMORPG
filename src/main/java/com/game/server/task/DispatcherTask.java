package com.game.server.task;

import com.google.protobuf.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName DispatcherTask
 * @Description 分发任务
 * @Author DELL
 * @Date 2019/8/14 14:30
 * @Version 1.0
 */
@Slf4j
public class DispatcherTask implements Runnable {
    /**
     * 处理器
     */
    private SimpleChannelInboundHandler<? extends Message> simpleChannelInboundHandler;
    /**
     * ctx
     */
    private ChannelHandlerContext ctx;
    /**
     * 协议信息
     */
    private Message msg;

    public DispatcherTask(SimpleChannelInboundHandler<? extends Message> simpleChannelInboundHandler, ChannelHandlerContext ctx, Message msg) {
        this.simpleChannelInboundHandler = simpleChannelInboundHandler;
        this.ctx = ctx;
        this.msg = msg;
    }

    @Override
    public void run() {
        try {
            simpleChannelInboundHandler.channelRead(ctx,msg);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("任务分发出现异常。。。。");
        }
    }
}
