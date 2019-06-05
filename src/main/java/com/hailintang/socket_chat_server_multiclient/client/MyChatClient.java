package com.hailintang.socket_chat_server_multiclient.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @ClassName MyChatClient
 * @Description TODO
 * @Author DELL
 * @Date 2019/5/2311:27
 * @Version 1.0
 */
public class MyChatClient {
    public static void main(String[] args) {
        MyChatClient myChatClient = new MyChatClient();
        myChatClient.start();
    }

    private void start() {
        EventLoopGroup clientGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(clientGroup).channel(NioSocketChannel.class).handler(new MyChatClientInitializer());

            Channel channel = bootstrap.connect("localhost", 8899).sync().channel();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
           for (;;){
               channel.writeAndFlush(bufferedReader.readLine()+"\r\n");
           }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            clientGroup.shutdownGracefully();
        }



    }
}
