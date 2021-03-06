package com.game.npc.service;

import com.game.event.beanevent.TalkEvent;
import com.game.event.manager.EventMap;
import com.game.npc.bean.ConcreteNPC;
import com.game.protobuf.protoc.MsgNpcInfoProto;
import com.game.role.bean.ConcreteRole;
import com.game.user.manager.LocalUserMap;
import com.game.utils.CacheUtils;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * @ClassName NpcService
 * @Description npc服务
 * @Author DELL
 * @Date 2019/8/12 10:23
 * @Version 1.0
 */
@Service
public class NpcService {
    /**
     * 谈话事件
     */
    @Autowired
    private TalkEvent talkEvent;
    /**
     * 事件容器map
     */
    @Autowired
    private EventMap eventMap;
    /**
     * 和npc谈话
     * @param channel channel
     * @param requestNpcInfo npc请求信息
     * @return 协议信息
     */
    public MsgNpcInfoProto.ResponseNpcInfo talkToNpc(Channel channel, MsgNpcInfoProto.RequestNpcInfo requestNpcInfo) {
        //获取npc名字
        String npcName = requestNpcInfo.getNpcName();
        //获取userId
        Integer userId = LocalUserMap.getChannelUserMap().get(channel);
        //获取role
        ConcreteRole role = LocalUserMap.getUserRoleMap().get(userId);
        //获取当前玩家所在场景
        final int mapId = role.getConcreteMap().getId();
        //获取场景的npc
        List<Integer> npcIdList = CacheUtils.mapIdNpcIdMap().get(mapId);
        //和npc交谈
        String content = talkNPC(npcIdList,role.getName(),npcName);
        //如果是NPC1好，触发事件
        if(npcName.equals("NPC1")){
            talkEvent.setRole(role);
            eventMap.submit(talkEvent);
        }

        return MsgNpcInfoProto.ResponseNpcInfo.newBuilder()
                .setType(MsgNpcInfoProto.RequestType.TALKTONPC)
                .setContent(content)
                .build();
    }

    /**
     * 和npc交谈
     * @param npcIdList
     * @param rolename
     * @param npcName
     * @return
     */
    public String talkNPC(List<Integer> npcIdList,String rolename,String npcName){
        if(npcIdList==null){
            return "该地图没有NPC:"+npcName;
        }
        Iterator<Integer> iterator = npcIdList.iterator();
        while(iterator.hasNext()){
            //遍历npc
            Integer npcId = iterator.next();
            ConcreteNPC concreteNPC = CacheUtils.getNpcMap().get(npcId);
            //匹配npc名字
            if (concreteNPC.getName().equals(npcName)) {
                return npcName+":"+"你好!"+rolename+","+concreteNPC.getContent();
            }
        }
        return "该地图没有NPC:"+npcName;
    }
}
