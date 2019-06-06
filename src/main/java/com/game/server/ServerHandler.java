package com.game.server;

import com.game.utils.MapUtils;
import com.game.utils.RequestTask;
import com.game.utils.ThreadPoolUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * @ClassName ServerHandler
 * @Description 服务端处理器
 * @Author DELL
 * @Date 2019/5/2714:15
 * @Version 1.0
 */
@Component("ServerHandler")
public class ServerHandler extends SimpleChannelInboundHandler<Object> {
    /**
     * 获取线程池
     */
    private final static ExecutorService workerThreadService = ThreadPoolUtils.getThreadPool();

    /**
     * channelActive
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().writeAndFlush("from server:成功连接服务端");
    }

    /**
     * channelRead0
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        //接收请求
        String content = (String)msg;
        //处理请求
        RequestTask requestTask = new RequestTask(content,ctx);
        String socket = ctx.channel().remoteAddress().toString();
        Map<String, RequestTask> taskMap = MapUtils.getTaskMap();
        taskMap.put(socket, requestTask);
        workerThreadService.execute(requestTask);
    }

    /**
     * exceptionCaught
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().closeFuture();
    }


}
