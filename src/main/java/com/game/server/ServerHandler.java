package com.game.server;

import com.game.dispatcher.DataCodeor;
import com.game.dispatcher.HandleThread;
import com.game.dispatcher.MyAnnotationUtil;
import com.game.dispatcher.ReqThread;
import com.game.map.Map_Mapping;
import com.game.mapper.MapMapper;
import com.game.utils.MapUtils;
import com.game.utils.SqlUtils;
import com.game.utils.ThreadUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

/**
 * @ClassName ServerHandler
 * @Description TODO
 * @Author DELL
 * @Date 2019/5/2714:15
 * @Version 1.0
 */
@Component("ServerHandler")
public class ServerHandler extends SimpleChannelInboundHandler<Object> {
    private final static ExecutorService workerThreadService = newBlockingExecutorsUseCallerRun(Runtime.getRuntime().availableProcessors() * 2);
    /**
     *  自定义线程池
     * @param size
     * @return
     */
    public static ExecutorService newBlockingExecutorsUseCallerRun(int size) {
        return new ThreadPoolExecutor(size, size, 0L, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>(),
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        try {
                            executor.getQueue().put(r);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().writeAndFlush("from server:成功连接服务端");

    }
    @Deprecated
    private void loadMapData() {
        SqlSession session = SqlUtils.getSession();
        try {
            MapMapper mapper = session.getMapper(MapMapper.class);
            List<Map_Mapping> map_mapping = mapper.getMap_Mapping();
            Iterator<Map_Mapping> iterator = map_mapping.iterator();
            while(iterator.hasNext()){
                Map_Mapping mapMapping = iterator.next();
                MapUtils.getListRole().add(mapMapping);
            }
        }finally {
            session.close();
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        //接收请求
        String content = (String)msg;
        //处理请求
        workerThreadService.execute(new Runnable() {
            @Override
            public void run() {
                //业务逻辑处理
                String result = MyAnnotationUtil.requestService(content);
                //加密
                result = DataCodeor.enCodeor(result);
                //把内容存放在仓库
//                if(result!=null){
//                    MapUtils.getResultCollectionsInstance().put(ctx.channel().remoteAddress().toString(),result);
//                    notifyAll();
//                }
                if (result!=null){
                    ctx.channel().writeAndFlush(result);
                }
            }
        });
//            //输出内容
//            workerThreadService.execute(new Runnable() {
//                @Override
//                public void run() {
//                    String res = MapUtils.getResultCollectionsInstance().remove(ctx.channel().remoteAddress().toString());
//                    ctx.channel().writeAndFlush(res);
//                }
//            });

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().closeFuture();
    }


}
