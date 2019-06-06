package com.game.utils;

import com.game.dispatcher.DataCodeor;
import com.game.dispatcher.MyAnnotationUtil;
import io.netty.channel.ChannelHandlerContext;

/**
 * @ClassName RequestTask
 * @Description 任务处理类
 * @Author DELL
 * @Date 2019/6/6 15:02
 * @Version 1.0
 */
public class RequestTask implements Runnable {
    private String content;
    private ChannelHandlerContext ctx;
    public RequestTask(String content, ChannelHandlerContext ctx){
        this.content = content;
        this.ctx = ctx;
    }
    @Override
    public void run() {

        //业务逻辑处理
        String result = MyAnnotationUtil.requestService(content);
        //加密
        result = DataCodeor.enCodeor(result);
        //写出
        if (result!=null){
            ctx.channel().writeAndFlush(result);
        }
    }
}
