package com.hailintang.protobuf_netty;

import com.sun.corba.se.impl.interceptors.PICurrent;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * @ClassName MyServerInitializer
 * @Description TODO
 * @Author DELL
 * @Date 2019/5/2710:10
 * @Version 1.0
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("protobufDecoder",new ProtobufDecoder(PersonInfo.Student.getDefaultInstance()));
        pipeline.addLast("protobufVarint32FrameDecoder",new ProtobufVarint32FrameDecoder());
        pipeline.addLast("protobufEncoder",new ProtobufEncoder());
        pipeline.addLast("protobufVarint32LengthFieldPrepender",new ProtobufVarint32LengthFieldPrepender());

        pipeline.addLast("myServerHandler",new MyServerHandler());
    }
}
