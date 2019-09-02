package com.game.map.task;

import com.game.duplicate.manager.RoleAndMap;
import com.game.map.bean.ConcreteMap;
import com.game.npc.bean.ConcreteMonster;
import com.game.protobuf.protoc.MsgBossInfoProto;
import com.game.role.bean.ConcreteRole;
import io.netty.channel.Channel;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName DispatcherTask
 * @Description boss攻击角色AI
 * @Author DELL
 * @Date 2019/7/8 16:02
 * @Version 1.0
 */
public class BossAutoAttackTask implements Runnable{
    /**
     * 地图
     */
    private ConcreteMap map;
    /**
     * 角色
     */
    private ConcreteRole role;
    /**
     * 容器map
     */
    private Map<String,ConcreteMonster> bossMap;

    public BossAutoAttackTask(ConcreteRole role, Map<String, ConcreteMonster> bossMap,ConcreteMap map) {
        this.map = map;
        this.role = role;
        this.bossMap = bossMap;
    }

    /**
     * 执行方法
     */
    @Override
    public void run() {
        Set<Map.Entry<String, ConcreteMonster>> entries = bossMap.entrySet();

        Iterator<Map.Entry<String, ConcreteMonster>> iterator = entries.iterator();
        //遍历地图的每一个boss攻击角色
        while (iterator.hasNext()) {
            Map.Entry<String, ConcreteMonster> next = iterator.next();
            ConcreteMonster boss = next.getValue();
            //boss攻击角色
            bossAttack(boss,role);
        }

    }

    /**
     * boss发动攻击
     * @param boss boss
     * @param role 角色
     */
    private void bossAttack(ConcreteMonster boss, ConcreteRole role) {
        //boss的攻击力
        Integer attack = boss.getAttack();
        //角色的当前血量
        int curHp = role.getCurHp();
        //角色被攻击，减少响应血量
        role.setCurHp(curHp-attack);
        //channel
        Channel channel = role.getChannel();
        //content
        String content = boss.getName()+"攻击"+role.getName()+"\t角色的血量："+role.getCurHp();
        //set
        RoleAndMap.varHp = role.getCurHp();
        //返回消息
        MsgBossInfoProto.ResponseBossInfo responseBossInfo = MsgBossInfoProto.ResponseBossInfo.newBuilder()
                .setType(MsgBossInfoProto.RequestType.ENTERDUPLICATE)
                .setContent(content)
                .build();
        //发送协议
        channel.writeAndFlush(responseBossInfo);
        //检验
        if(role.getCurHp()<=0){
            String content2 = "角色死亡，执行副本任务失败";
            //打包信息
            MsgBossInfoProto.ResponseBossInfo responseBossInfo2 = MsgBossInfoProto.ResponseBossInfo.newBuilder()
                    .setType(MsgBossInfoProto.RequestType.ENTERDUPLICATE)
                    .setContent(content2)
                    .build();
            //返回消息
            channel.writeAndFlush(responseBossInfo2);
            //移除任务
            role.getQueue().remove();
            //销毁副本
            map =null;
        }
    }
}
