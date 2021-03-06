package com.game.utils;

import io.netty.channel.ChannelHandlerContext;

/**
 * @ClassName ProtobufUtils
 * @Description protobuf工具类
 * @Author DELL
 * @Date 2019/8/19 12:28
 * @Version 1.0
 */
public class ProtobufUtils {
    /**
     * 发送协议
     */
    public static void sendProtobufMessage(ChannelHandlerContext ctx,Object protobufObject){
        ctx.writeAndFlush(protobufObject);
    }
}
