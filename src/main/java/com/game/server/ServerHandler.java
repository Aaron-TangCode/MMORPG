package com.game.server;

import com.game.user.threadpool.UserThreadPool;
import com.game.utils.RequestTask;
import com.game.utils.ThreadPoolUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.stereotype.Component;

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
        //封装任务
        RequestTask requestTask = new RequestTask(content,ctx);
        //客户端的唯一标识
        int id = Math.abs(ctx.channel().id().hashCode());
        int modIndex = id% UserThreadPool.DEFAULT_THREAD_POOL_SIZE;
        UserThreadPool.ACCOUNT_SERVICE[modIndex].execute(requestTask);
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
