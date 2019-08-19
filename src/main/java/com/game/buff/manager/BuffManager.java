package com.game.buff.manager;

import com.game.buff.bean.ConcreteBuff;
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

           int newMp = tempMp>role.getTotalMp()?role.getTotalMp():tempMp;

           role.setCurMp(newMp);

            role.getChannel().writeAndFlush("自动增加蓝量:"+buff.getMp()+"\t"+role.getName()+"的当前蓝量:"+role.getCurMp());
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
            int newHp = tempHp>role.getTotalHp()?role.getTotalHp():tempHp;
            //更新角色属性
            role.setCurHp(newHp);
            //输出
            role.getChannel().writeAndFlush("自动增加血量:"+buff.getHp()+"\t"+role.getName()+"的当前血量:"+role.getCurHp());
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
        if(lastDefend==0){
            role.setDefend(tempDefend+curDefend);
            buff.setDefend(curDefend);
            role.getChannel().writeAndFlush("根据血量增加盾");
        }
        //血量发生了变化
        if(curDefend!=tempDefend&&lastDefend!=0){
            role.setDefend(tempDefend);
            role.getChannel().writeAndFlush("根据血量增加盾");
        }
        return;
    }
}
