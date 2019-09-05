package com.game.buff.manager;

import com.game.buff.bean.ConcreteBuff;
import com.game.protobuf.protoc.MsgSkillInfoProto;
import com.game.role.bean.ConcreteRole;

/**
 * @ClassName BlueBuff
 * @Description 蓝buff
 * @Author DELL
 * @Date 2019/9/5 18:05
 * @Version 1.0
 */

public class BlueBuff implements IBuff {
    @Override
    public void executeBuff(ConcreteBuff buff, ConcreteRole role) {
        if(role.getCurMp()<role.getTotalMp()){
            int tempMp = role.getCurMp()+buff.getMp();
            //新蓝量
            int newMp = tempMp>role.getTotalMp()?role.getTotalMp():tempMp;
            //增加血量
            int addMp = tempMp>role.getTotalMp()?role.getTotalMp()-role.getCurMp():buff.getMp();
            role.setCurMp(newMp);
            String content = "自动增加蓝量:"+addMp+"\t"+role.getName()+"的当前蓝量:"+role.getCurMp();
            MsgSkillInfoProto.ResponseSkillInfo responseSkillInfo = MsgSkillInfoProto.ResponseSkillInfo.newBuilder()
                    .setType(MsgSkillInfoProto.RequestType.USESKILL)
                    .setContent(content)
                    .build();
            role.getChannel().writeAndFlush(responseSkillInfo);
        }
    }
}
