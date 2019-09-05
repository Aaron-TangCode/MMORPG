package com.game.buff.manager;

import com.game.buff.bean.ConcreteBuff;
import com.game.protobuf.protoc.MsgSkillInfoProto;
import com.game.role.bean.ConcreteRole;

/**
 * @ClassName DefendBuff
 * @Description 防守buff
 * @Author DELL
 * @Date 2019/9/5 18:06
 * @Version 1.0
 */
public class DefendBuff implements IBuff {
    @Override
    public void executeBuff(ConcreteBuff buff, ConcreteRole role) {
        //获取角色防御力
        Integer lastDefend = buff.getDefend();
        Integer curDefend = role.getDefend();
        //获取角色当前血量
        int curHp = role.getCurHp();
        int addDefend = (int)(curHp*0.1);
        int tempDefend = lastDefend+addDefend;
        String content = "根据血量增加盾";
        if(lastDefend==0){
            role.setDefend(tempDefend+curDefend);
            buff.setDefend(curDefend);
        }
        //血量发生了变化
        if(curDefend!=tempDefend&&lastDefend!=0){
            role.setDefend(tempDefend);
        }
        MsgSkillInfoProto.ResponseSkillInfo responseSkillInfo = MsgSkillInfoProto.ResponseSkillInfo.newBuilder()
                .setType(MsgSkillInfoProto.RequestType.USESKILL)
                .setContent(content)
                .build();
        role.getChannel().writeAndFlush(responseSkillInfo);
        return;
    }
}
