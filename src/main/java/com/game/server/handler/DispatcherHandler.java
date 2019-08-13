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
    @Autowired
    private NpcHandler npcHandler;
    @Autowired
    private GoodsHandler goodsHandler;
    @Autowired
    private SkillHandler skillHandler;
    @Autowired
    private EquipHandler equipHandler;
    @Autowired
    private DuplicateHandler duplicateHandler;
    @Autowired
    private ShopHandler shopHandler;
    @Autowired
    private ChatHandler chatHandler;
    @Autowired
    private EmailHandler emailHandler;

    private static Map<Integer, SimpleChannelInboundHandler<? extends Message>> handlerMap = new HashMap<>();

    private DispatcherHandler(){}
    @PostConstruct
    public void injectData(){
        //user
        handlerMap.put(REQUEST_USERINFO_PROTO.protoCode, userInfoHandler);
        //role
        handlerMap.put(REQUEST_ROLEINFO_PROTO.protoCode,roleHandler);
        //map
        handlerMap.put(REQUEST_MAPINFO_PROTO.protoCode,mapHandler);
        //npc
        handlerMap.put(REQUEST_NPCINFO_PROTO.protoCode,npcHandler);
        //goods
        handlerMap.put(REQUEST_GOODSINFO_PROTO.protoCode,goodsHandler);
        //skill
        handlerMap.put(REQUEST_SKILLINFO_PROTO.protoCode,skillHandler);
        //equip
        handlerMap.put(REQUEST_EQUIPINFO_PROTO.protoCode,equipHandler);
        //duplicate
        handlerMap.put(REQUEST_BOSSINFO_PROTO.protoCode,duplicateHandler);
        //shop
        handlerMap.put(REQUEST_SHOPINFO_PROTO.protoCode,shopHandler);
        //chat
        handlerMap.put(REQUEST_CHATINFO_PROTO.protoCode,chatHandler);
        //email
        handlerMap.put(REQUEST_EMAILINFO_PROTO.protoCode,emailHandler);
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception{
        Integer protoNum = LocalMessageMap.messageMap.get(msg.getClass());
        handlerMap.get(protoNum).channelRead(ctx, msg);
    }
}
