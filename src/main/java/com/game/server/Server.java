package com.game.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName Server
 * @Description 服务端
 * @Author DELL
 * @Date 2019/5/2714:08
 * @Version 1.0
 */
@Slf4j
@Component("Server")
public class Server {
    @Autowired
    private ServerInitializer serverInitializer;
    /**
     *   默认端口
     */
    private int port = 8899;

    /**
     * 启动服务端
     * @param port
     */
    public void start(int port) {
        log.info("start server ");

        this.port = port;
        this.run();


    }

    /**
     * 运行服务端
     */
    private void run() {
        //接收请求
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //处理请求
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(serverInitializer);
            //绑定端口
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //关闭
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
