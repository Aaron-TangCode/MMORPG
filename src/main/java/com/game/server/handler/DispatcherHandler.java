package com.game.server.handler;

import com.game.server.local.LocalMessageMap;
import com.game.server.local.LocalUserData;
import com.game.server.task.DispatcherTask;
import com.game.user.manager.LocalUserMap;
import com.game.user.threadpool.UserThreadPool;
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
    @Autowired
    private TradeHandler tradeHandler;
    @Autowired
    private AuctionHandler auctionHandler;
    @Autowired
    private RankHandler rankHandler;
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
        //trade
        handlerMap.put(REQUEST_TRADEINFO_PROTO.protoCode, tradeHandler);
        //auction
        handlerMap.put(REQUEST_AUCTIONINFO_PROTO.protoCode,auctionHandler);
        //rank
        handlerMap.put(REQUEST_RANKINFO_PROTO.protoCode,rankHandler);
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception{
        //获取请求号
        Integer protoNum = LocalMessageMap.messageMap.get(msg.getClass());

        //获取任务参数
        SimpleChannelInboundHandler<? extends Message> simpleChannelInboundHandler = handlerMap.get(protoNum);

        //创建任务
        DispatcherTask task = new DispatcherTask(simpleChannelInboundHandler,ctx,msg);


        long userId = LocalUserData.getUserId();
        int threadIndex;
        if(userId<=0){
            //未登陆
            threadIndex = UserThreadPool.getThreadIndex(ctx.channel().id());
        }else{
            //已登陆
            Integer useId = LocalUserMap.getChannelUserMap().get(ctx.channel());
            threadIndex = UserThreadPool.getThreadIndex(useId);
        }
        //把任务放到线程池
        UserThreadPool.getThreadPool(threadIndex).submit(task);

    }
}
