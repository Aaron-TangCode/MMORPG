package com.hailintang.socket_chat_server_multiclient.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @ClassName MyChatServerHandler
 * @Description TODO
 * @Author DELL
 * @Date 2019/5/2310:54
 * @Version 1.0
 */
public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {
    //加static后，每次请求时都是同一个channelGroup;如果不加static，每次请求都是一个新的
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 需求：一个客户端发送消息，其他客户端可以收到消息。
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.forEach(groupch->{
            if(groupch!=channel){
                groupch.writeAndFlush(channel.remoteAddress()+"发送消息:"+msg+"\n");
            }else{
                channel.writeAndFlush("[自己]:"+msg+"\n");
            }
        });
    }

    /**
     * 加入客户端
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        //通知
        channelGroup.writeAndFlush("[服务端]"+channel.remoteAddress()+"加入\n");
        //添加channel到channelGroup
        channelGroup.add(channel);
    }

    /**
     * 移除客户端，channelGroup会自动触发移除操作
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[服务端]"+channel.remoteAddress()+"离开\n");
    }

    /**
     * 激活客户端
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress()+"上线");
    }

    /**
     * 客户端失活
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress()+"下线");
    }

    /**
     * 捕捉异常
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
