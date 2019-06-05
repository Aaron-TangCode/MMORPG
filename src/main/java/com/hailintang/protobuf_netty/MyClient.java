package com.hailintang.protobuf_netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @ClassName MyClient
 * @Description TODO
 * @Author DELL
 * @Date 2019/5/2710:22
 * @Version 1.0
 */
public class MyClient {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup clientGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(clientGroup).channel(NioSocketChannel.class)
                    .handler(new MyClientInitializer());

            ChannelFuture localhost = bootstrap.connect("localhost", 8899).sync();
            localhost.channel().closeFuture().sync();
        }finally {
            clientGroup.shutdownGracefully();
        }



    }
}
