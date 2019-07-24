package com.game.event.handler;

/**
 * @ClassName KillMonsterHandler
 * @Description TODO
 * @Author DELL
 * @Date 2019/7/24 18:33
 * @Version 1.0
 */
public class KillMonsterHandler implements IHandler {

    @Override
    public void exec() {
        System.out.println("恭喜你已经完成1个杀怪任务");
    }
}
