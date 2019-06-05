package com.hailintang.gameclient2;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @ClassName Client
 * @Description TODO
 * @Author DELL
 * @Date 2019/5/2712:18
 * @Version 1.0
 */
public class Client {
    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    private void start() {
        EventLoopGroup clientGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(clientGroup).channel(NioSocketChannel.class)
                    .handler(new ClientInitializer());

            ChannelFuture channelFuture = bootstrap.connect("localhost", 8899).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
           clientGroup.shutdownGracefully();
        }



    }
}
