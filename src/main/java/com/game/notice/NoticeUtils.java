package com.game.notice;

import com.game.map.bean.ConcreteMap;
import com.game.npc.bean.ConcreteMonster;
import com.game.protobuf.protoc.MsgSkillInfoProto;
import com.game.role.bean.ConcreteRole;
import com.game.utils.CacheUtils;

import java.util.List;

/**
 * @ClassName NoticeUtils
 * @Description 通知工具类
 * @Author DELL
 * @Date 2019/6/14 14:30
 * @Version 1.0
 */
public class NoticeUtils {

    /**
     * 通知所有角色
     * @param monster 怪兽
     */
    public static void notifyAllRoles(ConcreteMonster monster){
        //通知该场景下的所有玩家
        int mapId = 0;
        for (int i = 0; i < CacheUtils.getMonsterMapMappingList().size(); i++) {
            //怪兽id
            int mid = CacheUtils.getMonsterMapMappingList().get(i).getMonsterId();
            if(mid==monster.getId()){
                //地图
               mapId = CacheUtils.getMonsterMapMappingList().get(i).getMapId();
               break;
            }
        }
        //获取地图
        ConcreteMap map = CacheUtils.getMapMap().get(mapId);
        //获取角色
        List<ConcreteRole> roleList = map.getRoleList();
        if(roleList==null){
            return;
        }
        //遍历
        for (int i = 0; i < roleList.size(); i++) {
            String msg = monster.getName()+"状态:"+monster.getState();
            //玩家收到通知
                MsgSkillInfoProto.ResponseSkillInfo responseSkillInfo = MsgSkillInfoProto.ResponseSkillInfo.newBuilder()
                        .setContent(msg)
                        .setType(MsgSkillInfoProto.RequestType.USESKILL)
                        .build();
                //发送消息
                roleList.get(i).getChannel().writeAndFlush(responseSkillInfo);
        }
    }

}
