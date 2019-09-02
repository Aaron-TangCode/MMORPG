package com.game.task.handler;

import com.game.protobuf.protoc.MsgTaskInfoProto;
import com.game.task.service.TaskService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.game.server.request.RequestTaskInfoType.*;

/**
 * @ClassName TaskHandler
 * @Description TODO
 * @Author DELL
 * @Date 2019/8/20 12:24
 * @Version 1.0
 */
@Component
@ChannelHandler.Sharable
public class TaskHandler extends SimpleChannelInboundHandler<MsgTaskInfoProto.RequestTaskInfo> {
    /**
     * 任务服务
     */
    @Autowired
    private TaskService taskService;

    private MsgTaskInfoProto.ResponseTaskInfo responseTaskInfo;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgTaskInfoProto.RequestTaskInfo requestTaskInfo) throws Exception {
        //Channel
        Channel channel = ctx.channel();

        int typeNum = requestTaskInfo.getType().getNumber();
        //分发任务
        switch (typeNum) {
            case QUERYRECEIVABLETASK :
                responseTaskInfo = taskService.queryReceivableTask(channel,requestTaskInfo);
                break;
            case QUERYRECEIVEDTASK :
                responseTaskInfo = taskService.queryReceivedTask(channel,requestTaskInfo);
                break;
            case RECEIVETASK :
                responseTaskInfo = taskService.receiveTask(channel,requestTaskInfo);
                break;
            case DISCARDTASK:
                responseTaskInfo = taskService.discardTask(channel,requestTaskInfo);
                break;
                default:
                    break;
        }
        //发送协议
        ctx.writeAndFlush(responseTaskInfo);
    }
}
