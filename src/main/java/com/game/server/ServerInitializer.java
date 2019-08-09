package com.game.server;

import com.game.server.codec.CustomProtobufDecoder;
import com.game.server.codec.CustomProtobufEncoder;
import com.game.server.handler.DispatcherHandler;
import com.game.server.handler.ServerIdleStateHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName ServerInitializer
 * @Description 服务端initializer
 * @Author DELL
 * @Date 2019/5/2714:13
 * @Version 1.0
 */
@Component
public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    private EventExecutorGroup businessGroup = new DefaultEventExecutorGroup(2);
    @Autowired
    private DispatcherHandler dispatcherHandler;
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new ServerIdleStateHandler());
        //编码器
        // 添加自定义编码解码器
        pipeline.addLast("decoder",new CustomProtobufDecoder());
        pipeline.addLast("encoder",new CustomProtobufEncoder());

        //请求分发
        pipeline.addLast(businessGroup, dispatcherHandler);
    }
}
