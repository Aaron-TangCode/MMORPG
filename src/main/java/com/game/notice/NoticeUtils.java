package com.game.notice;

import com.game.npc.bean.ConcreteMonster;
import com.game.role.bean.ConcreteRole;
import com.game.utils.MapUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName NoticeUtils
 * @Description TODO
 * @Author DELL
 * @Date 2019/6/14 14:30
 * @Version 1.0
 */
public class NoticeUtils {


    public static void notifyAllRoles(ConcreteMonster monster){
        //通知该场景下的所有玩家
        int mapId = 0;
        for (int i = 0; i < MapUtils.getMonsterMapMappingList().size(); i++) {
            int mid = MapUtils.getMonsterMapMappingList().get(i).getMonsterId();
            if(mid==monster.getId()){
               mapId = MapUtils.getMonsterMapMappingList().get(i).getMapId();
               break;
            }
        }
        //通过mapId找到该场景下的所有玩家
        Map<String, ConcreteRole> roleMap = MapUtils.getMapRolename_Role();
        Set<Map.Entry<String, ConcreteRole>> entrySet = roleMap.entrySet();
        Iterator<Map.Entry<String, ConcreteRole>> iterator = entrySet.iterator();
        while(iterator.hasNext()){
            Map.Entry<String, ConcreteRole> name = iterator.next();
            //所有玩家的地图id
            int id = name.getValue().getConcreteMap().getId();
            //id匹配，通知玩家
            if(mapId==id){
                String msg = monster.getName()+"状态:"+monster.getState();
                ConcreteRole role = name.getValue();
                //玩家收到通知
                role.getCtx().channel().writeAndFlush(msg);
            }
        }
    }

}
