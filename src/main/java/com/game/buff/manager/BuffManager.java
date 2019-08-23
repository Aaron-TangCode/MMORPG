package com.game.buff.manager;

import com.game.buff.bean.ConcreteBuff;
import com.game.protobuf.protoc.MsgSkillInfoProto;
import com.game.role.bean.ConcreteRole;
import org.springframework.stereotype.Component;

/**
 * @ClassName BuffManager
 * @Description buff管理器
 * @Author DELL
 * @Date 2019/6/20 17:42
 * @Version 1.0
 */
@Component
public class BuffManager {

    /**
     * 蓝Buff
     * @param buff buff
     * @param role 角色
     */
    public static void blueBuff(ConcreteBuff buff,ConcreteRole role){

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

    /**
     * 红Buff
     * @param buff buff
     * @param role 角色
     */
    public static void redBuff(ConcreteBuff buff,ConcreteRole role){
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

    /**
     * 防守buff
     * @param buff buff
     * @param role 角色
     */
    public static void defendBuff(ConcreteBuff buff,ConcreteRole role){
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
