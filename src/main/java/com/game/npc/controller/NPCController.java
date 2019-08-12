package com.game.npc.controller;

import com.game.dispatcher.RequestAnnotation;
import com.game.role.bean.ConcreteRole;
import com.game.utils.MapUtils;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * @ClassName NPCController
 * @Description NPC控制器
 * @Author DELL
 * @Date 2019/6/11 14:13
 * @Version 1.0
 */
@Controller
@RequestAnnotation("/npc")
public class NPCController {
    /**
     * 和NPC谈话
     * @return
     */
    @RequestAnnotation("/taklwithnpc")
    public String talkWithNPC(String rolename,String npcname){
        //获取当前玩家所在场景
        ConcreteRole concreteRole = MapUtils.getMapRolename_Role().get(rolename);
        final int mapId = concreteRole.getConcreteMap().getId();
        //获取场景的npc
        List<Integer> npcIdList = MapUtils.mapIdnpcIdMap().get(mapId);
        //和npc交谈
//        return talkNPC(npcIdList,rolename,npcname);
        return null;
    }

//    public static String talkNPC(List<Integer> npcIdList,String rolename,String npcname){
//        Iterator<Integer> iterator = npcIdList.iterator();
//        while(iterator.hasNext()){
//            //遍历npc
//            Integer npcId = iterator.next();
//            ConcreteNPC concreteNPC = MapUtils.getNpcMap().get(npcId);
//            //匹配npc名字
//            if (concreteNPC.getName().equals(npcname)) {
//                return npcname+":"+"你好!"+rolename+","+concreteNPC.getContent();
//            }
//        }
//        return "该地图没有NPC:"+npcname;
//    }
}
