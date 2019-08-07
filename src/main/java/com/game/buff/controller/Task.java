package com.game.buff.controller;

import com.game.buff.bean.ConcreteBuff;
import com.game.buff.manager.BuffManager;
import com.game.role.bean.ConcreteRole;

/**
 * @ClassName Task
 * @Description TODO
 * @Author DELL
 * @Date 2019/6/20 17:29
 * @Version 1.0
 */
public class Task implements Runnable {
    private ConcreteBuff buff;
    private ConcreteRole role;
    public Task(ConcreteBuff buff, ConcreteRole role){
        this.buff = buff;
        this.role = role;
    }
    @Override
    public void run() {
        BuffManager.startBuff(buff,role);
        BuffManager.defendBuff(buff,role);
    }


}
