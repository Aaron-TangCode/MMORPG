package com.game.server.handler;

import com.game.server.local.LocalMessageMap;
import com.google.protobuf.Message;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

import static com.game.protobuf.message.MessageType.*;

/**
 * @ClassName DispatcherHandler
 * @Description TODO
 * @Author DELL
 * @Date 2019/8/7 16:43
 * @Version 1.0
 */
@Component
@ChannelHandler.Sharable
public class DispatcherHandler extends SimpleChannelInboundHandler<Message> {
    @Autowired
    private UserInfoHandler userInfoHandler;
    @Autowired
    private RoleHandler roleHandler;
    @Autowired
    private MapHandler mapHandler;
    private static Map<Integer, SimpleChannelInboundHandler<? extends Message>> handlerMap = new HashMap<>();

    private DispatcherHandler(){}
    @PostConstruct
    public void injectData(){
        handlerMap.put(REQUEST_USERINFO_PROTO.protoCode, userInfoHandler);
        handlerMap.put(REQUEST_ROLEINFO_PROTO.protoCode,roleHandler);
        handlerMap.put(REQUEST_MAPINFO_PROTO.protoCode,mapHandler);
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception{
        Integer protoNum = LocalMessageMap.messageMap.get(msg.getClass());
        handlerMap.get(protoNum).channelRead(ctx, msg);
    }
}
