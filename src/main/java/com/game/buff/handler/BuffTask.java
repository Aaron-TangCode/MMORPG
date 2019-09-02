package com.game.buff.handler;

import com.game.buff.bean.ConcreteBuff;
import com.game.buff.manager.BuffManager;
import com.game.role.bean.ConcreteRole;

import static com.game.buff.handler.BuffType.*;

/**
 * @ClassName BuffTask
 * @Description Buff任务
 * @Author DELL
 * @Date 2019/6/20 17:29
 * @Version 1.0
 */
public class BuffTask implements Runnable {
    /**
     * buff
     */
    private ConcreteBuff buff;
    /**
     * 角色
     */
    private ConcreteRole role;
    public BuffTask(ConcreteBuff buff, ConcreteRole role){
        this.buff = buff;
        this.role = role;
    }
    @Override
    public void run() {
        //获取buff名字
        String name = buff.getName();
        switch (name){
            case RED :
                BuffManager.redBuff(buff,role);
                break;
            case BLUE :
                BuffManager.blueBuff(buff,role);
                break;
            case DEFEND:
                BuffManager.defendBuff(buff,role);
                break;
                default:
                    break;
        }
    }
}
