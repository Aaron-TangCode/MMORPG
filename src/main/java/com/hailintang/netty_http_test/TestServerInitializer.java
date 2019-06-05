package com.hailintang.netty_http_test;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @ClassName TestServerInitializer
 * @Description TODO
 * @Author DELL
 * @Date 2019/5/2215:55
 * @Version 1.0
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    /**
     * 一旦客户端连接到服务端，TestServerInitializer对象就会被创建，initChannel就会被调用
     * @param ch
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline channelPipeline = ch.pipeline();

        channelPipeline.addLast("HttpServerCodec",new HttpServerCodec());
        channelPipeline.addLast("TestHttpServerHandler",new TestHttpServerHandler());
    }
}
