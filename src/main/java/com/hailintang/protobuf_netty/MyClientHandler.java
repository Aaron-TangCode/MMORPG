package com.hailintang.protobuf_netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @ClassName MyClientHandler
 * @Description TODO
 * @Author DELL
 * @Date 2019/5/2710:30
 * @Version 1.0
 */
public class MyClientHandler extends SimpleChannelInboundHandler<PersonInfo.Student> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PersonInfo.Student msg) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        PersonInfo.Student student = PersonInfo.Student.newBuilder()
                .setAddress("广州")
                .setAge(22)
                .setName("汤海麟").build();
        ctx.channel().writeAndFlush(student);
    }
}
