package com.game.buff.handler;

import com.game.buff.bean.ConcreteBuff;
import com.game.buff.manager.BuffManager;
import com.game.buff.manager.IBuff;
import com.game.role.bean.ConcreteRole;

import java.util.Map;

import static com.game.buff.handler.BuffType.getBuffType;

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
        //获取buff类型
        BuffType buffType = getBuffType(name);
        //map
        Map<BuffType, IBuff> buffTypeIBuffMap = BuffManager.getiBuffMap();
        //iBuff
        IBuff iBuff = buffTypeIBuffMap.get(buffType);
        //获取buffManager
        BuffManager buffManager = new BuffManager(iBuff);
        //执行buff
        buffManager.execBuff(buff,role);
    }
}
