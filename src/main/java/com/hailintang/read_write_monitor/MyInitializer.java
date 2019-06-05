package com.hailintang.read_write_monitor;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName MyInitializer
 * @Description TODO
 * @Author DELL
 * @Date 2019/5/2314:47
 * @Version 1.0
 */
public class MyInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("idleStateHandler",new IdleStateHandler(5,7,10, TimeUnit.SECONDS));
        pipeline.addLast("myHandler",new MyHandler());
    }
}
