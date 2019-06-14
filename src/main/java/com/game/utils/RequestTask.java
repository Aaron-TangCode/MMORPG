package com.game.utils;

import com.game.dispatcher.DataCodeor;
import com.game.dispatcher.MyAnnotationUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName RequestTask
 * @Description 任务处理类
 * @Author DELL
 * @Date 2019/6/6 15:02
 * @Version 1.0
 */
public class RequestTask implements Runnable {
    public static Map<String, Channel> stringChannelMap = new HashMap<>();
    private String content;
    private ChannelHandlerContext ctx;
    public RequestTask(String content, ChannelHandlerContext ctx){
        this.content = content;
        this.ctx = ctx;
    }

    @Override
    public void run() {
        //业务逻辑处理
        String result = MyAnnotationUtil.requestService(content,ctx);
        //加密
        result = DataCodeor.enCodeor(result);
        //写出
        if (result!=null){
            ctx.channel().writeAndFlush(result);
        }
    }


}
