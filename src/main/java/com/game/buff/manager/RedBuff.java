package com.game.buff.manager;

import com.game.buff.bean.ConcreteBuff;
import com.game.protobuf.protoc.MsgSkillInfoProto;
import com.game.role.bean.ConcreteRole;
import org.springframework.stereotype.Component;

/**
 * @ClassName RedBuff
 * @Description 红Buff
 * @Author DELL
 * @Date 2019/9/5 18:05
 * @Version 1.0
 */
@Component
public class RedBuff implements IBuff {
    @Override
    public void executeBuff(ConcreteBuff buff, ConcreteRole role) {
        //比较当前血量和总血量
        if(role.getCurHp()<role.getTotalHp()){
            //注入属性
            int tempHp = role.getCurHp()+buff.getHp();
            //新血量
            int newHp = tempHp>role.getTotalHp()?role.getTotalHp():tempHp;
            //增加血量
            int addHp = tempHp>role.getTotalHp()?role.getTotalHp()-role.getCurHp():buff.getHp();
            //更新角色属性
            role.setCurHp(newHp);
            String content = "自动增加血量:"+addHp+"\t"+role.getName()+"的当前血量:"+role.getCurHp();
            MsgSkillInfoProto.ResponseSkillInfo responseSkillInfo = MsgSkillInfoProto.ResponseSkillInfo.newBuilder()
                    .setType(MsgSkillInfoProto.RequestType.USESKILL)
                    .setContent(content)
                    .build();
            //输出
            role.getChannel().writeAndFlush(responseSkillInfo);
        }
    }
}
