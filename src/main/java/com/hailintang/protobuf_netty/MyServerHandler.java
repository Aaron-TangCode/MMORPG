package com.hailintang.protobuf_netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @ClassName MyServerHandler
 * @Description TODO
 * @Author DELL
 * @Date 2019/5/2710:18
 * @Version 1.0
 */
public class MyServerHandler extends SimpleChannelInboundHandler<PersonInfo.Student> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PersonInfo.Student msg) throws Exception {
        System.out.println(msg.getAddress());
        System.out.println(msg.getAge());
        System.out.println(msg.getName());
    }
}
